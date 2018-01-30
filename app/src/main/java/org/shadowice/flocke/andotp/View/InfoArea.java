package org.shadowice.flocke.andotp.View;

import android.graphics.ColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.shadowice.flocke.andotp.Database.Entry;
import org.shadowice.flocke.andotp.R;
import org.shadowice.flocke.andotp.Utilities.EntryThumbnail;
import org.shadowice.flocke.andotp.Utilities.Settings;
import org.shadowice.flocke.andotp.Utilities.Tools;

public class InfoArea {
    private Context context;
    private CardView card;

    private LinearLayout customPeriodLayout;
    private ImageView thumbnailImg;
    private TextView value;
    private TextView label;
    private TextView tags;
    private TextView customPeriod;

    public InfoArea(AppCompatActivity activity) {
        context = activity;
        card = activity.findViewById(R.id.card_view_info_area);

        value = card.findViewById(R.id.valueText);
        thumbnailImg = card.findViewById(R.id.thumbnailImg);
        label = card.findViewById(R.id.textViewLabel);
        tags = card.findViewById(R.id.textViewTags);
        customPeriodLayout = card.findViewById(R.id.customPeriodLayout);
        customPeriod = card.findViewById(R.id.customPeriod);

        ImageButton menuButton = card.findViewById(R.id.menuButton);
        ImageButton copyButton = card.findViewById(R.id.copyButton);

        ColorFilter colorFilter = Tools.getThemeColorFilter(context, android.R.attr.textColorSecondary);

        if(menuButton != null) menuButton.getDrawable().setColorFilter(colorFilter);
        if(copyButton != null) copyButton.getDrawable().setColorFilter(colorFilter);
    }

    public void setup(Entry e) {
        Settings settings = new Settings(context);
        final String tokenFormatted = Tools.formatToken(e.getCurrentOTP(), settings.getTokenSplitGroupSize());

        if(this.label != null) {
            this.label.setText(e.getLabel());
        }

        if(value != null) {
            value.setText(tokenFormatted);
            value.setTag(e.getCurrentOTP());
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < e.getTags().size(); i++) {
            stringBuilder.append(e.getTags().get(i));
            if(i < e.getTags().size() - 1) {
                stringBuilder.append(", ");
            }
        }

        if(this.tags != null) {
            this.tags.setText(stringBuilder.toString());

            if (!e.getTags().isEmpty()) {
                this.tags.setVisibility(View.VISIBLE);
            } else {
                this.tags.setVisibility(View.GONE);
            }
        }

        if(thumbnailImg != null) {
            thumbnailImg.setVisibility(settings.getThumbnailVisible() ? View.VISIBLE : View.GONE);

            int thumbnailSize = settings.getThumbnailSize();
            if (settings.getThumbnailVisible()) {
                thumbnailImg.setImageBitmap(EntryThumbnail.getThumbnailGraphic(context, e.getLabel(), thumbnailSize, e.getThumbnail()));
            }
        }

        if(e.hasNonDefaultPeriod()) {
            if(customPeriodLayout != null) customPeriodLayout.setVisibility(View.VISIBLE);
            if(customPeriod != null) customPeriod.setText(String.format(context.getString(R.string.format_custom_period), e.getPeriod()));
        } else {
            if(customPeriodLayout != null) customPeriodLayout.setVisibility(View.GONE);
        }

        if(label != null) label.setTextSize(settings.getLabelSize());
        if(tags != null) tags.setTextSize(0.75f * settings.getLabelSize());

        if(thumbnailImg != null) {
            thumbnailImg.getLayoutParams().height = settings.getThumbnailSize();
            thumbnailImg.getLayoutParams().width = settings.getThumbnailSize();
            thumbnailImg.requestLayout();
        }

        if(label != null) {
            if (settings.getScrollLabel()) {
                label.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                label.setHorizontallyScrolling(true);
                label.setSelected(true);
            } else {
                label.setEllipsize(TextUtils.TruncateAt.END);
                label.setHorizontallyScrolling(false);
                label.setSelected(false);
            }
        }
    }

    public boolean isShowing() {
        return card.getVisibility() == View.VISIBLE;
    }

    public void show() {
        card.setVisibility(View.VISIBLE);
    }

    public void hide() {
        card.setVisibility(View.GONE);
    }
}
