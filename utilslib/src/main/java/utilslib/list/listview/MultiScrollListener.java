package utilslib.list.listview;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MultiScrollListener implements OnScrollListener {
    private List<OnScrollListener> scrollListeners = new ArrayList<>();

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        for (OnScrollListener l: scrollListeners) {
            l.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        for (OnScrollListener l:scrollListeners) {
            l.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void addListener(OnScrollListener listener) {
        scrollListeners.add(listener);
    }

    public void removeListener(OnScrollListener listener) {
        scrollListeners.remove(listener);
    }

    public boolean isEmpty() {
        return scrollListeners.isEmpty();
    }

    public static void addScrollListener(ListView listview, OnScrollListener listener){
        if (listview == null || listener == null)  {
            return;
        }
        OnScrollListener originListener = getScrollListenerFromListView(listview);
        if (originListener instanceof MultiScrollListener) {
            (((MultiScrollListener)originListener)).addListener(listener);
        } else {
            MultiScrollListener multiScrollListener = new MultiScrollListener();
            if (originListener != null) {
                multiScrollListener.addListener(originListener);
            }
            multiScrollListener.addListener(listener);
            listview.setOnScrollListener(multiScrollListener);
        }
    }

    public static void removeScrollListener(ListView listview, OnScrollListener listener) {
        if (listview == null || listener == null) {
            return;
        }
        OnScrollListener originListener = getScrollListenerFromListView(listview);
        if (originListener == listener) {
            listview.setOnScrollListener(null);
        } else {
            if (originListener instanceof MultiScrollListener) {
                MultiScrollListener multiScrollListener = (MultiScrollListener)originListener;
                multiScrollListener.removeListener(listener);
                if (multiScrollListener.isEmpty()) {
                    listview.setOnScrollListener(null);
                }
            }
        }
    }

    private static OnScrollListener getScrollListenerFromListView(ListView lv) {
        if (lv == null) {
            return null;
        }
        try {
            Field scrollListenerField = ListView.class.getDeclaredField("mOnScrollListener");
            return (OnScrollListener)scrollListenerField.get(lv);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
