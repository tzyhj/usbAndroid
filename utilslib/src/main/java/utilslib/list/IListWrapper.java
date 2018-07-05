package utilslib.list;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import utilslib.helper.LoadController;


public interface IListWrapper {
    public static final int STATE_INITIAL = 0;
    public static final int STATE_LOAD_MORE = 1;
    public static final int STATE_LOAD_FAILED = 2;
    public static final int STATE_LOAD_COMPLETE = 3;

    void init(LoadController<?> loadController);

    void setState(@ListState int curState);

    @IntDef({STATE_INITIAL, STATE_LOAD_MORE, STATE_LOAD_FAILED, STATE_LOAD_COMPLETE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ListState {

    }

}
