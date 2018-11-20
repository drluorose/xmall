package cn.exrick.search.service;


import cn.exrick.manager.dto.EsInfo;

import java.io.IOException;

/**
 * @author Exrickx
 */
public interface SearchItemService {

	/**
	 * 同步索引
	 * @return
	 */
	int importAllItems() throws IOException;

	/**
	 * 获取ES基本信息
	 * @return
	 */
	EsInfo getEsInfo();
}
