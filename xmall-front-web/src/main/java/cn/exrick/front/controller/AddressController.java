package cn.exrick.front.controller;

import cn.exrick.common.annotation.JwtAuth;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.common.utils.XmallLists;
import cn.exrick.manager.dto.front.CityDto;
import cn.exrick.manager.dto.front.CountryDto;
import cn.exrick.manager.dto.front.req.IdReq;
import cn.exrick.manager.pojo.TbAddress;
import cn.exrick.manager.pojo.TbCity;
import cn.exrick.manager.pojo.TbCountry;
import cn.exrick.sso.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Exrickx
 */
@RestController
@Api(description = "收货地址")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @JwtAuth
    @RequestMapping(value = "/member/allCountry")
    @ApiOperation(value = "获取所有的国家信息")
    public Result<List<CountryDto>> getAllCountry() {
        List<TbCountry> allCountryList = addressService.getCountryList();
        List<CountryDto> countryDtos = XmallLists.transform(allCountryList, CountryDto::new);
        Collections.sort(countryDtos);
        return new ResultUtil<List<CountryDto>>().setData(countryDtos);
    }

    @JwtAuth
    @RequestMapping(value = "/member/citiesCountry")
    @ApiOperation(value = "获取某个国家下的城市")
    public Result<List<CityDto>> getCountryCites(@RequestBody IdReq idReq) {
        if (Objects.isNull(idReq) || Objects.isNull(idReq.getId())) {
            throw new IllegalArgumentException("参数错误");
        }
        List<TbCity> cites = this.addressService.getCountryCites(idReq.getId());
        List<CityDto> cityDtos = XmallLists.transform(cites, CityDto::new);
        return new ResultUtil<List<CityDto>>().setData(cityDtos);
    }

    @JwtAuth
    @RequestMapping(value = "/member/addressList", method = RequestMethod.POST)
    @ApiOperation(value = "获得所有收货地址")
    public Result<List<TbAddress>> addressList(@RequestBody TbAddress tbAddress) {

        List<TbAddress> list = addressService.getAddressList(tbAddress.getUserId());
        return new ResultUtil<List<TbAddress>>().setData(list);
    }

    @JwtAuth
    @RequestMapping(value = "/member/address", method = RequestMethod.POST)
    @ApiOperation(value = "通过id获得收货地址")
    public Result<TbAddress> address(@RequestBody TbAddress tbAddress) {

        TbAddress address = addressService.getAddress(tbAddress.getAddressId());
        return new ResultUtil<TbAddress>().setData(address);
    }

    @JwtAuth
    @RequestMapping(value = "/member/addAddress", method = RequestMethod.POST)
    @ApiOperation(value = "添加收货地址")
    public Result<Object> addAddress(@RequestBody TbAddress tbAddress) {

        int result = addressService.addAddress(tbAddress);
        return new ResultUtil<Object>().setData(result);
    }

    @JwtAuth
    @RequestMapping(value = "/member/updateAddress", method = RequestMethod.POST)
    @ApiOperation(value = "编辑收货地址")
    public Result<Object> updateAddress(@RequestBody TbAddress tbAddress) {

        int result = addressService.updateAddress(tbAddress);
        return new ResultUtil<Object>().setData(result);
    }

    @JwtAuth
    @RequestMapping(value = "/member/delAddress", method = RequestMethod.POST)
    @ApiOperation(value = "删除收货地址")
    public Result<Object> delAddress(@RequestBody TbAddress tbAddress) {

        int result = addressService.delAddress(tbAddress);
        return new ResultUtil<Object>().setData(result);
    }
}
