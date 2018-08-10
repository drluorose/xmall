package cn.exrick.manager.dto.front;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Exrickx
 */
@Data
public class SearchResult implements Serializable {

    private Long recordCount;

    private int totalPages;

    private List<SearchItem> itemList;
}
