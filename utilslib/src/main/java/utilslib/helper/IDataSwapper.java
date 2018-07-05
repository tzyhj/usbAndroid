package utilslib.helper;

import java.util.List;


public interface IDataSwapper<DT> {
    /**
     * Swap all datas
     */
    void swapData(List<? extends DT> list);
    /**
     * Append data to the end of current list
     */
    void appendData(List<? extends DT> list);
}
