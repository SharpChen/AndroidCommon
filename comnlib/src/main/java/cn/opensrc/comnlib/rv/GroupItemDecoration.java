package cn.opensrc.comnlib.rv;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cn.opensrc.comnlib.bean.GroupDataBean;

/**
 * Author:       sharp
 * Created on:   19/10/2016 5:28 PM
 * Description:
 * Revisions:
 */

public class GroupItemDecoration extends RecyclerView.ItemDecoration {

    private List<GroupDataBean> mGroupDataBeans;
    private Paint paint;
    private int groupDividerHeight = 100;
    private int groupTextSize = 50;
    private int groupTextColor = Color.DKGRAY;
    private int groupBgColor = Color.LTGRAY;
    private final int DRAW = 0;
    private final int DRAW_COVER = 1;
    private boolean isTopHover = true;

    public GroupItemDecoration(List<GroupDataBean> mGroupDataBeans) {
        if (mGroupDataBeans == null)
            throw new RuntimeException("The data is null!");
        this.mGroupDataBeans = mGroupDataBeans;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(groupTextSize);
    }

    /**
     * 画在 RecyclerView 下面一层
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        // 不是 RecyclerView 中所有ItemView的总个数,而是屏幕中一屏能显示的ItemView总个数
        int rvChildCount = parent.getChildCount();

        for (int i = 0; i < rvChildCount; i++) {

            View itemView = parent.getChildAt(i);
            RecyclerView.LayoutParams itemLayoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            int pos = itemLayoutParams.getViewAdapterPosition();

            if (pos == 0 || (!mGroupDataBeans.get(pos).getFirstLetter().equalsIgnoreCase(mGroupDataBeans.get(pos - 1).getFirstLetter())))
                drawGroup(c,parent,itemView,DRAW);

        }

    }

    /**
     * 画在 RecyclerView 上面一层
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (!isTopHover) return;
        int pos = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
        View itemView = parent.findViewHolderForLayoutPosition(pos).itemView;
        drawGroup(c,parent,itemView,DRAW_COVER);
    }

    /**
     * 画在 RecyclerView 同一层
     * 将所画 View 嵌进 RecyclerView 中,但不占用位置,即原来位于位置 2 的ItemView现在的位置依然是2
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position == 0)
            outRect.set(0, groupDividerHeight, 0, 0);
        else if (!mGroupDataBeans.get(position).getFirstLetter().equalsIgnoreCase(mGroupDataBeans.get(position - 1).getFirstLetter())) {
            outRect.set(0, groupDividerHeight, 0, 0);
        }
    }

    /**
     * 画分组装饰视图
     *
     * @param c the Canvas object
     * @param rv the RecyclerView Object
     * @param itemView the RecyclerView item
     * @param flag the flag use to Distinguish between onDraw and onDrawOver method in this class
     */
    private void drawGroup(Canvas c, RecyclerView rv, View itemView, int flag) {

        RecyclerView.LayoutParams itemLayoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        int textYBaseLinePos = 0;
        int bgRectTop = 0;
        int bgRectBottom = 0;

        int pos = itemLayoutParams.getViewAdapterPosition();
        int textXPos = rv.getPaddingLeft() + itemView.getPaddingLeft() + itemView.getLeft();

        if (DRAW == flag) {
            // childView.getTop() = childViewMarginTop + parentViewPaddingTop + groupDividerHeight,与其它属性无关
            textYBaseLinePos = itemView.getTop() - itemLayoutParams.topMargin - rv.getPaddingTop() - groupDividerHeight / 2 + groupTextSize / 2 - 3;
            bgRectTop = itemView.getTop() - groupDividerHeight
                    - (itemLayoutParams.topMargin + itemLayoutParams.bottomMargin) - (itemView.getPaddingTop() + itemView.getPaddingBottom())
                    - rv.getPaddingTop();
            bgRectBottom = itemView.getTop();
        }
        else if (DRAW_COVER == flag) {
            textYBaseLinePos = groupDividerHeight/2 + groupTextSize/2 - 3;
            bgRectTop = 0;
            bgRectBottom = groupDividerHeight;
        }
        int bgRectLeft = rv.getPaddingLeft();
        int bgRectRight = rv.getRight();

        paint.setColor(groupBgColor);
        c.drawRect(bgRectLeft, bgRectTop, bgRectRight, bgRectBottom, paint);
        paint.setColor(groupTextColor);
        c.drawText(mGroupDataBeans.get(pos).getFirstLetter(), textXPos, textYBaseLinePos, paint);

    }

    /**
     * @return the Paint object of draw
     */
    public Paint getPaint() {
        return paint;
    }

    /**
     * @param groupTextSize the text size
     */
    public void setGroupTextSize(int groupTextSize) {
        this.groupTextSize = groupTextSize;
        paint.setTextSize(groupTextSize);
    }

    /**
     * @param groupTextColor the text color
     */
    public void setGroupTextColor(int groupTextColor) {
        this.groupTextColor = groupTextColor;
    }

    /**
     * @param groupBgColor the group decoration background color
     */
    public void setGroupBgColor(int groupBgColor) {
        this.groupBgColor = groupBgColor;
    }

    /**
     * @param groupDividerHeight the group decoration height
     */
    public void setGroupDividerHeight(int groupDividerHeight) {
        this.groupDividerHeight = groupDividerHeight;
    }

    /**
     * @param topHover 是否需要头部悬停
     */
    public void setTopHover(boolean topHover) {
        isTopHover = topHover;
    }

}
