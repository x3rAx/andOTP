package org.shadowice.flocke.andotp.View;


import android.view.View;

public interface EntryViewCallback {
    void onMoveEventStart();
    void onMoveEventStop();

    void onMenuButtonClicked(View parentView, int position);
    void onCopyButtonClicked(String text, int position);
    void onTap(int position);
}