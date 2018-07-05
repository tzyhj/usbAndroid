package utilslib.helper;

import android.view.View;
import android.view.ViewGroup;


public interface ILoadViewCreator<DT> {

    View createView(ViewGroup parent, LoadMoreHelper<DT> loadHelper);
}
