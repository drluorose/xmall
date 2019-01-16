package cn.exrick.front.controller.address;

import cn.exrick.common.annotation.JwtAuth;
import cn.exrick.common.enums.ValidStatusEnum;
import cn.exrick.common.exception.XmallException;
import cn.exrick.common.pojo.MemberInfoDto;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.res.ResultCodeEnum;
import cn.exrick.common.utils.Iterables;
import cn.exrick.common.utils.ObjectId;
import cn.exrick.common.utils.ResultResUtil;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.front.annotation.MemberInfo;
import cn.exrick.front.controller.address.req.AddAddressReq;
import cn.exrick.front.controller.address.req.UpdateAddressReq;
import cn.exrick.manager.dto.front.AddressDto;
import cn.exrick.manager.pojo.TbAddress;
import cn.exrick.manager.pojo.TbAddressItem;
import cn.exrick.sso.service.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "收货地址")
public class AddressController {

    @Reference
    private AddressService addressService;

    @JwtAuth
    @RequestMapping(value = "/member/addressList", method = RequestMethod.POST)
    @ApiOperation(value = "获得所有收货地址")
    public Result<List<AddressDto>> addressList(@MemberInfo MemberInfoDto memberInfoDto) {

        List<AddressDto> list = addressService.getAddressList(memberInfoDto.getMid());
        return ResultResUtil.successWithData(list);
    }

    @JwtAuth
    @RequestMapping(value = "/member/addAddress", method = RequestMethod.POST)
    @ApiOperation(value = "添加收货地址")
    public Result addAddress(@RequestBody AddAddressReq addAddressReq, @MemberInfo MemberInfoDto memberInfoDto) {
        if (!checkParamOk(addAddressReq)) {
            return ResultResUtil.errorWithoutData(ResultCodeEnum.CODE_NORMAL_FAIL_PARAM_ERROR);
        }
        TbAddress tbAddress = new TbAddress();
        tbAddress.setId(ObjectId.getId());
        fillParam(addAddressReq, memberInfoDto, tbAddress);
        List<TbAddressItem> items = getTbAddressItems(addAddressReq, tbAddress);
        items.forEach(item -> {
            item.setId(ObjectId.getId());
        });
        try {
            addressService.addAddress(tbAddress, items);
        } catch (XmallException e) {
            log.error("到达上限", e);
            return ResultResUtil.errorWithoutData(ResultCodeEnum.CODE_ADDRESS_ADD_FAIL);
        } catch (IllegalArgumentException e) {
            log.error("参数错误", e);
            return ResultResUtil.errorWithoutData(ResultCodeEnum.CODE_NORMAL_FAIL_PARAM_ERROR);
        }
        return ResultResUtil.success();
    }

    private List<TbAddressItem> getTbAddressItems(AddAddressReq addAddressReq, TbAddress tbAddress) {
        List<TbAddressItem> items = Lists.newArrayList();
        Iterables.forEach(addAddressReq.getAddressCode(), (index, item) -> {
            TbAddressItem tbAddressItem = new TbAddressItem();
            tbAddressItem.setAddressId(tbAddress.getId());
            tbAddressItem.setCode(item.getCode());
            tbAddressItem.setName(item.getName());
            tbAddressItem.setLevel(index);
            items.add(tbAddressItem);
        });
        return items;
    }

    private void fillParam(@RequestBody AddAddressReq addAddressReq, @MemberInfo MemberInfoDto memberInfoDto, TbAddress tbAddress) {
        tbAddress.setAddress(addAddressReq.getAddress());
        tbAddress.setIsDefault(addAddressReq.getIsDefault());
        tbAddress.setMid(memberInfoDto.getMid());
        tbAddress.setTel(addAddressReq.getTel());
        tbAddress.setUserName(addAddressReq.getUserName());
        tbAddress.setValid(ValidStatusEnum.VALID);
    }

    @JwtAuth
    @RequestMapping(value = "/member/updateAddress", method = RequestMethod.POST)
    @ApiOperation(value = "编辑收货地址")
    public Result updateAddress(@RequestBody UpdateAddressReq updateAddressReq, @MemberInfo MemberInfoDto memberInfoDto) {
        if (!checkParamOk(updateAddressReq)) {
            return ResultResUtil.errorWithoutData(ResultCodeEnum.CODE_NORMAL_FAIL_PARAM_ERROR);
        }
        if (StringUtils.isBlank(updateAddressReq.getId())) {
            return ResultResUtil.errorWithoutData(ResultCodeEnum.CODE_NORMAL_FAIL_PARAM_ERROR);
        }
        TbAddress tbAddress = new TbAddress();
        tbAddress.setId(updateAddressReq.getId());
        fillParam(updateAddressReq, memberInfoDto, tbAddress);
        List<TbAddressItem> items = getTbAddressItems(updateAddressReq, tbAddress);
        try {
            addressService.updateAddress(tbAddress, items);
        } catch (IllegalArgumentException e) {
            return ResultResUtil.errorWithoutData(ResultCodeEnum.CODE_NORMAL_FAIL_PARAM_ERROR);
        }
        return ResultResUtil.success();
    }

    private boolean checkParamOk(AddAddressReq addAddressReq) {
        if (Objects.isNull(addAddressReq)) {
            return false;
        }
        if (CollectionUtils.isEmpty(addAddressReq.getAddressCode()) || addAddressReq.getAddressCode().size() != 3) {
            return false;
        }
        return true;
    }

    @JwtAuth
    @RequestMapping(value = "/member/delAddress", method = RequestMethod.POST)
    @ApiOperation(value = "删除收货地址")
    public Result<Object> delAddress(@RequestBody TbAddress tbAddress) {

        int result = addressService.delAddress(tbAddress);
        return new ResultUtil<Object>().setData(result);
    }
}
