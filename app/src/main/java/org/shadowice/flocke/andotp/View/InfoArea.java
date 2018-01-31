package org.shadowice.flocke.andotp.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewPropertyAnimator;
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
    private EntryViewCallback callback;

    private LinearLayout customPeriodLayout;
    private ImageView thumbnailImg;
    private TextView value;
    private TextView label;
    private TextView tags;
    private TextView customPeriod;

    private ImageButton menuButton;
    private ImageButton copyButton;

    private Entry entry;
    private int adapterPosition;

    Settings settings;

    public InfoArea(AppCompatActivity activity) {
        context = activity;

        settings = new Settings(context);

        card = activity.findViewById(R.id.card_view_info_area);

        value = card.findViewById(R.id.valueText);
        thumbnailImg = card.findViewById(R.id.thumbnailImg);
        label = card.findViewById(R.id.textViewLabel);
        tags = card.findViewById(R.id.textViewTags);
        customPeriodLayout = card.findViewById(R.id.customPeriodLayout);
        customPeriod = card.findViewById(R.id.customPeriod);

        menuButton = card.findViewById(R.id.menuButton);
        copyButton = card.findViewById(R.id.copyButton);

        ColorFilter colorFilter = Tools.getThemeColorFilter(context, android.R.attr.textColorSecondary);

        if(menuButton != null) menuButton.getDrawable().setColorFilter(colorFilter);
        if(copyButton != null) copyButton.getDrawable().setColorFilter(colorFilter);
    }

    public void setup(Entry e, int adapterPos) {
        this.entry = e;
        adapterPosition = adapterPos;

        redraw();

        if(menuButton != null) menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.onMenuButtonClicked(view, adapterPosition);
            }
        });

        if(copyButton != null) copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.onCopyButtonClicked(value.getTag().toString(), adapterPosition);
            }
        });

        if(card != null) card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onTap(adapterPosition);
            }
        });

        resize();
    }

    public void redraw() {
        if(entry == null)
            return;

        final String tokenFormatted = Tools.formatToken(entry.getCurrentOTP(), settings.getTokenSplitGroupSize());

        if(this.label != null) {
            this.label.setText(entry.getLabel());
        }

        if(value != null) {
            value.setText(tokenFormatted);
            value.setTag(entry.getCurrentOTP());
        }

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < entry.getTags().size(); i++) {
            stringBuilder.append(entry.getTags().get(i));
            if(i < entry.getTags().size() - 1) {
                stringBuilder.append(", ");
            }
        }

        if(this.tags != null) {
            this.tags.setText(stringBuilder.toString());

            if (!entry.getTags().isEmpty()) {
                this.tags.setVisibility(View.VISIBLE);
            } else {
                this.tags.setVisibility(View.GONE);
            }
        }

        if(thumbnailImg != null) {
            thumbnailImg.setVisibility(settings.getThumbnailVisible() ? View.VISIBLE : View.GONE);

            int thumbnailSize = settings.getThumbnailSize();
            if (settings.getThumbnailVisible()) {
                thumbnailImg.setImageBitmap(EntryThumbnail.getThumbnailGraphic(context, entry.getLabel(), thumbnailSize, entry.getThumbnail()));
            }
        }

        if(entry.hasNonDefaultPeriod()) {
            if(customPeriodLayout != null) customPeriodLayout.setVisibility(View.VISIBLE);
            if(customPeriod != null) customPeriod.setText(String.format(context.getString(R.string.format_custom_period), entry.getPeriod()));
        } else {
            if(customPeriodLayout != null) customPeriodLayout.setVisibility(View.GONE);
        }
    }

    public void resize() {
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

    public void setCallback(EntryViewCallback cb) {
        this.callback = cb;
    }

    public boolean isShowing() {
        return card.getVisibility() == View.VISIBLE;
    }

    public void show() {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            card.setY(-1.0f * card.getHeight());
        } else {
            card.setX(-1.0f * card.getWidth());
        }

        card.setVisibility(View.VISIBLE);
        card.setAlpha(0.0f);

        ViewPropertyAnimator animator = card.animate();

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            animator.translationY(0);
        } else {
            animator.translationX(0);
        }
        animator.alpha(1.0f).setListener(null);
    }

    public void hide() {
        ViewPropertyAnimator animator = card.animate();

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            animator.translationY(-1.0f * card.getHeight());
        } else {
            animator.translationX(-1.0f * card.getWidth());
        }
        animator.alpha(0.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        card.setVisibility(View.GONE);
                    }
                });
    }
}
