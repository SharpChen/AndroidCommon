package cn.opensrc.comnlib.rv;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Author:       sharp
 * Created on:   20/10/2016 3:13 PM
 * Description:
 * Revisions:
 */

public class NormalDivItemDecoration extends RecyclerView.ItemDecoration {

    private Paint paint;
    private int divHeight = 1;

    public NormalDivItemDecoration() {
        this.paint = new Paint();
        paint.setAntiAlias(true);
        setPaintColor(Color.LTGRAY);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int rvChildCount = parent.getChildCount();

        for (int i = 0; i < rvChildCount; i++) {

            View itemView = parent.getChildAt(i);
            RecyclerView.LayoutParams itemLayoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            int pos = itemLayoutParams.getViewAdapterPosition();
            if (pos > 0 )
                c.drawRect(0,itemView.getTop() - divHeight,parent.getRight(),itemView.getTop(),paint);

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position > 0) outRect.set(0, divHeight, 0, 0);
    }

    /**
     * @param paintColor the Paint object draw color
     */
    public void setPaintColor(int paintColor) {
        paint.setColor(paintColor);
    }

    /**
     * @param divHeight the divder decoration height
     */
    public void setDivHeight(int divHeight) {
        this.divHeight = divHeight;
    }

}
