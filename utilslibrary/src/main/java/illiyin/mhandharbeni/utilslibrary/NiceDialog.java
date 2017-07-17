package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

/**
 * Created by root on 17/07/17.
 */

public class NiceDialog {
    Context context;

    public NiceDialog(Context context) {
        this.context = context;
    }

    public void standart(String message){
        new LovelyStandardDialog(context)
                .setTopColorRes(R.color.colorAccent)
                .setButtonsColorRes(R.color.colorPrimary)
                .setTitle(message)
                .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "positive clicked", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    public void info(String title, String message){
        new LovelyInfoDialog(context)
                .setTopColorRes(R.color.colorAccent)
                .setNotShowAgainOptionEnabled(0)
                .setNotShowAgainOptionChecked(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}
