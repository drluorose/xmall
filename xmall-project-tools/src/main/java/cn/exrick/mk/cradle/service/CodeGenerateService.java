package cn.exrick.mk.cradle.service;

import com.douyu.wsd.cradle.vo.GenerateReq;

import java.io.File;
import java.io.IOException;

public interface CodeGenerateService {

    File generate(GenerateReq req) throws IOException;
}
