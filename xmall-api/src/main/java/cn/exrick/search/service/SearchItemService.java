package cn.exrick.search.service;

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
}
