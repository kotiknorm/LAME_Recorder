package greenlab.com.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class UpsideDownText extends TextView {

    public UpsideDownText(Context context) {
        super(context);
    }

    public UpsideDownText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.save();
        float py = this.getHeight() / 2.0f;
        float px = this.getWidth() / 2.0f;
        canvas.rotate(180, px, py);
        super.onDraw(canvas);
        canvas.restore();
    }
}