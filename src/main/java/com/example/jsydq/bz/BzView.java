package com.example.jsydq.bz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by Lionheart on 2016/6/2.
 */
public class BzView extends View {

	private int width = 720;
	private int height = 1280;
	private int yejiaoX = 0;
	private int yejiaoY = 0;
	private Path path_1;
	private Path path_2;
	Bitmap currentBitmap = null;
	Bitmap nextBitmap = null;

	PointF mTouch = new PointF();
	PointF bStart1 = new PointF();
	PointF bControl1 = new PointF();
	PointF bTop1 = new PointF();
	PointF bEnd1 = new PointF();
	PointF bStart2 = new PointF();
	PointF bControl2 = new PointF();
	PointF bTop2 = new PointF();
	PointF bEnd2 = new PointF();

	float midX;
	float midY;
	float degress;
	float touch_yejiao_dis;
	ColorMatrixColorFilter colorMatrixColorFilter;
	Matrix matrix;
	
    float[] matrixArr = { 0, 0, 0, 0, 0, 0, 0, 0, 1.0f };
	boolean isrt_lb;
	float maxLength = (float) Math.hypot(width, height);
	int[] backShadowColors;
	int[] frontShadowColors;
	GradientDrawable backDrawableLR;
	GradientDrawable backDrawableRL;
	GradientDrawable folderDrawableLR;
	GradientDrawable folderDrawableRL;

	GradientDrawable frontDrawble_h_bt;
	GradientDrawable frontDrawable_h_tb;
	GradientDrawable frontDrawable_v_lr;
	GradientDrawable frontDrawable_v_rl;

	Paint paint;

	Scroller scroller;

	public BzView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public BzView(Context context, int width, int height) {
		super(context);
		// TODO Auto-generated constructor stub
		path_1 = new Path();
		path_2 = new Path();
		createDrawable();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		this.width = width;
		this.height = height;

		paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
		ColorMatrix cm = new ColorMatrix();
		float array[] = { 0.55f, 0, 0, 0, 80.0f, 0, 0.55f, 0, 0, 80.0f, 0, 0,
				0.55f, 0, 80.0f, 0, 0, 0, 0.2f, 0 };
		cm.set(array);
		colorMatrixColorFilter = new ColorMatrixColorFilter(cm);
		matrix = new Matrix();
		scroller = new Scroller(getContext());
		mTouch.x = 0.1f;
		mTouch.y = 0.1f;
	}


	public void calcCornerXY(float x, float y) {
		if (x <= width / 2)
            yejiaoX = 0;
		else
            yejiaoX = width;
		if (y <= height / 2)
			yejiaoY = 0;
		else
			yejiaoY = height;
		if ((yejiaoX == 0 && yejiaoY == height)
				|| (yejiaoX == width && yejiaoY == 0))
			isrt_lb = true;
		else
			isrt_lb = false;
	}

	public boolean doTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			mTouch.x = event.getRawX();
			mTouch.y = event.getRawY();
			this.invalidate();
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mTouch.x = event.getRawX();
			mTouch.y = event.getRawY();
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (canDragOver()) {

				startAnimation(700);
			} else {

				mTouch.x = yejiaoX+0.1f ;
				mTouch.y = yejiaoY+0.1f ;
			}
			this.invalidate();
			return true;
		}
		return true;
	}


	public PointF getCross(PointF P1, PointF P2, PointF P3, PointF P4) {
		PointF CrossP = new PointF();
		// 二元函数通式： y=ax+b
		float a1 = (P2.y - P1.y) / (P2.x - P1.x);
		float b1 = ((P1.x * P2.y) - (P2.x * P1.y)) / (P1.x - P2.x);

		float a2 = (P4.y - P3.y) / (P4.x - P3.x);
		float b2 = ((P3.x * P4.y) - (P4.x * P3.y)) / (P3.x - P4.x);
		CrossP.x = (b2 - b1) / (a1 - a2);
		CrossP.y = a1 * CrossP.x + b1;
		return CrossP;
	}

	private void calcPoints() {
		midX = (mTouch.x + yejiaoX) / 2;
		midY = (mTouch.y + yejiaoY) / 2;
		bControl1.x = midX - (yejiaoY - midY)
				* (yejiaoY - midY) / (yejiaoX - midX);
		bControl1.y = yejiaoY;
		bControl2.x = yejiaoX;
		bControl2.y = midY - (yejiaoX - midX)
				* (yejiaoX - midX) / (yejiaoY - midY);
		bStart1.x = bControl1.x - (yejiaoX - bControl1.x)
				/ 2;
		bStart1.y = yejiaoY;
		if (mTouch.x > 0 && mTouch.x < width) {
			if (bStart1.x < 0 || bStart1.x > width) {
				if (bStart1.x < 0)
					bStart1.x = width - bStart1.x;

				float f1 = Math.abs(yejiaoX - mTouch.x);
				float f2 = width * f1 / bStart1.x;
				mTouch.x = Math.abs(yejiaoX - f2);

				float f3 = Math.abs(yejiaoX - mTouch.x)
						* Math.abs(yejiaoY - mTouch.y) / f1;
				mTouch.y = Math.abs(yejiaoY - f3);

				midX = (mTouch.x + yejiaoX) / 2;
				midY = (mTouch.y + yejiaoY) / 2;

				bControl1.x = midX - (yejiaoY - midY)
						* (yejiaoY - midY) / (yejiaoX - midX);
				bControl1.y = yejiaoY;

				bControl2.x = yejiaoX;
				bControl2.y = midY - (yejiaoX - midX)
						* (yejiaoX - midX) / (yejiaoY - midY);
				bStart1.x = bControl1.x
						- (yejiaoX - bControl1.x) / 2;
			}
		}
		bStart2.x = yejiaoX;
		bStart2.y = bControl2.y - (yejiaoY - bControl2.y)
				/ 2;

		touch_yejiao_dis = (float) Math.hypot((mTouch.x - yejiaoX),
				(mTouch.y - yejiaoY));

		bEnd1 = getCross(mTouch, bControl1, bStart1,
                bStart2);
		bEnd2 = getCross(mTouch, bControl2, bStart1,
                bStart2);

		bTop1.x = (bStart1.x + 2 * bControl1.x + bEnd1.x) / 4;
		bTop1.y = (2 * bControl1.y + bStart1.y + bEnd1.y) / 4;
		bTop2.x = (bStart2.x + 2 * bControl2.x + bEnd2.x) / 4;
		bTop2.y = (2 * bControl2.y + bStart2.y + bEnd2.y) / 4;
	}

	private void drawCurrentPageArea(Canvas canvas, Bitmap bitmap, Path path) {
		path_1.reset();
		path_1.moveTo(bStart1.x, bStart1.y);
		path_1.quadTo(bControl1.x, bControl1.y, bEnd1.x,
                bEnd1.y);
		path_1.lineTo(mTouch.x, mTouch.y);
		path_1.lineTo(bEnd2.x, bEnd2.y);
		path_1.quadTo(bControl2.x, bControl2.y, bStart2.x,
                bStart2.y);
		path_1.lineTo(yejiaoX, yejiaoY);
		path_1.close();

        canvas.save();
        canvas.clipPath(path_1, Region.Op.XOR);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.restore();
	}

	private void drawNextPageAreaAndShadow(Canvas canvas, Bitmap bitmap) {
		path_2.reset();
		path_2.moveTo(bStart1.x, bStart1.y);
		path_2.lineTo(bTop1.x, bTop1.y);
		path_2.lineTo(bTop2.x, bTop2.y);
		path_2.lineTo(bStart2.x, bStart2.y);
		path_2.lineTo(yejiaoX, yejiaoY);
		path_2.close();

		degress = (float) Math.toDegrees(Math.atan2(bControl1.x
				- yejiaoX, bControl2.y - yejiaoY));
		int leftx;
		int rightx;
		GradientDrawable mBackShadowDrawable;
		if (isrt_lb) {
			leftx = (int) (bStart1.x);
			rightx = (int) (bStart1.x + touch_yejiao_dis / 4);
			mBackShadowDrawable = backDrawableLR;
		} else {
			leftx = (int) (bStart1.x - touch_yejiao_dis / 4);
			rightx = (int) bStart1.x;
			mBackShadowDrawable = backDrawableRL;
		}
		canvas.save();
		canvas.clipPath(path_1);
		canvas.clipPath(path_2, Region.Op.INTERSECT);
		canvas.drawBitmap(bitmap, 0, 0, null);
		canvas.rotate(degress, bStart1.x, bStart1.y);
		mBackShadowDrawable.setBounds(leftx, (int) bStart1.y, rightx,
				(int) (maxLength + bStart1.y));
		mBackShadowDrawable.draw(canvas);
		canvas.restore();
	}

	public void setBitmaps(Bitmap bm1, Bitmap bm2) {
		currentBitmap = bm1;
		nextBitmap = bm2;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(0xFFAAAAAA);
        calcPoints();
		drawCurrentPageArea(canvas, currentBitmap, path_1);
		drawNextPageAreaAndShadow(canvas, nextBitmap);
		drawCurrentPageShadow(canvas);
		drawCurrentBackArea(canvas, currentBitmap);
	}


	private void createDrawable() {
		int[] color = { 0x333333, 0xB0333333 };
		folderDrawableRL = new GradientDrawable(
				GradientDrawable.Orientation.RIGHT_LEFT, color);
		folderDrawableRL
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		folderDrawableLR = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, color);
		folderDrawableLR
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		backShadowColors = new int[] { 0xFF111111, 0x111111 };
		backDrawableRL = new GradientDrawable(
				GradientDrawable.Orientation.RIGHT_LEFT, backShadowColors);
		backDrawableRL.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		backDrawableLR = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, backShadowColors);
		backDrawableLR.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		frontShadowColors = new int[] { 0x80111111, 0x111111 };
		frontDrawable_v_lr = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, frontShadowColors);
		frontDrawable_v_lr
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);
		frontDrawable_v_rl = new GradientDrawable(
				GradientDrawable.Orientation.RIGHT_LEFT, frontShadowColors);
		frontDrawable_v_rl
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		frontDrawable_h_tb = new GradientDrawable(
				GradientDrawable.Orientation.TOP_BOTTOM, frontShadowColors);
		frontDrawable_h_tb
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);

		frontDrawble_h_bt = new GradientDrawable(
				GradientDrawable.Orientation.BOTTOM_TOP, frontShadowColors);
		frontDrawble_h_bt
				.setGradientType(GradientDrawable.LINEAR_GRADIENT);
	}


	public void drawCurrentPageShadow(Canvas canvas) {
		double degree;
		if (isrt_lb) {
			degree = Math.PI
					/ 4
					- Math.atan2(bControl1.y - mTouch.y, mTouch.x
							- bControl1.x);
		} else {
			degree = Math.PI
					/ 4
					- Math.atan2(mTouch.y - bControl1.y, mTouch.x
							- bControl1.x);
		}

		double d1 = (float) 25 * 1.414 * Math.cos(degree);
		double d2 = (float) 25 * 1.414 * Math.sin(degree);
		float x = (float) (mTouch.x + d1);
		float y;
		if (isrt_lb) {
			y = (float) (mTouch.y + d2);
		} else {
			y = (float) (mTouch.y - d2);
		}
		path_2.reset();
		path_2.moveTo(x, y);
		path_2.lineTo(mTouch.x, mTouch.y);
		path_2.lineTo(bControl1.x, bControl1.y);
		path_2.lineTo(bStart1.x, bStart1.y);
		path_2.close();
		float rotateDegrees;
		canvas.save();

		canvas.clipPath(path_1, Region.Op.XOR);
		canvas.clipPath(path_2, Region.Op.INTERSECT);
		int leftx;
		int rightx;
		GradientDrawable mCurrentPageShadow;
		if (isrt_lb) {
			leftx = (int) (bControl1.x);
			rightx = (int) bControl1.x + 25;
			mCurrentPageShadow = frontDrawable_v_lr;
		} else {
			leftx = (int) (bControl1.x - 25);
			rightx = (int) bControl1.x + 1;
			mCurrentPageShadow = frontDrawable_v_rl;
		}

		rotateDegrees = (float) Math.toDegrees(Math.atan2(mTouch.x
				- bControl1.x, bControl1.y - mTouch.y));
		canvas.rotate(rotateDegrees, bControl1.x, bControl1.y);
		mCurrentPageShadow.setBounds(leftx,
                (int) (bControl1.y - maxLength), rightx,
                (int) (bControl1.y));
		mCurrentPageShadow.draw(canvas);
		canvas.restore();

		path_2.reset();
		path_2.moveTo(x, y);
		path_2.lineTo(mTouch.x, mTouch.y);
		path_2.lineTo(bControl2.x, bControl2.y);
		path_2.lineTo(bStart2.x, bStart2.y);
		path_2.close();
		canvas.save();
		canvas.clipPath(path_1, Region.Op.XOR);
		canvas.clipPath(path_2, Region.Op.INTERSECT);
		if (isrt_lb) {
			leftx = (int) (bControl2.y);
			rightx = (int) (bControl2.y + 25);
			mCurrentPageShadow = frontDrawable_h_tb;
		} else {
			leftx = (int) (bControl2.y - 25);
			rightx = (int) (bControl2.y + 1);
			mCurrentPageShadow = frontDrawble_h_bt;
		}
		rotateDegrees = (float) Math.toDegrees(Math.atan2(bControl2.y
				- mTouch.y, bControl2.x - mTouch.x));
		canvas.rotate(rotateDegrees, bControl2.x, bControl2.y);
		float temp;
		if (bControl2.y < 0)
			temp = bControl2.y - height;
		else
			temp = bControl2.y;

		int hmg = (int) Math.hypot(bControl2.x, temp);
		if (hmg > maxLength)
			mCurrentPageShadow
					.setBounds((int) (bControl2.x - 25) - hmg, leftx,
							(int) (bControl2.x + maxLength) - hmg,
							rightx);
		else
			mCurrentPageShadow.setBounds(
					(int) (bControl2.x - maxLength), leftx,
					(int) (bControl2.x), rightx);

		mCurrentPageShadow.draw(canvas);
		canvas.restore();
	}
	private void drawCurrentBackArea(Canvas canvas, Bitmap bitmap) {
		int i = (int) (bStart1.x + bControl1.x) / 2;
		float f1 = Math.abs(i - bControl1.x);
		int i1 = (int) (bStart2.y + bControl2.y) / 2;
		float f2 = Math.abs(i1 - bControl2.y);
		float f3 = Math.min(f1, f2);
		path_2.reset();
		path_2.moveTo(bTop2.x, bTop2.y);
		path_2.lineTo(bTop1.x, bTop1.y);
		path_2.lineTo(bEnd1.x, bEnd1.y);
		path_2.lineTo(mTouch.x, mTouch.y);
		path_2.lineTo(bEnd2.x, bEnd2.y);
		path_2.close();
		GradientDrawable mFolderShadowDrawable;
		int left;
		int right;
		if (isrt_lb) {
			left = (int) (bStart1.x - 1);
			right = (int) (bStart1.x + f3 + 1);
			mFolderShadowDrawable = folderDrawableLR;
		} else {
			left = (int) (bStart1.x - f3 - 1);
			right = (int) (bStart1.x + 1);
			mFolderShadowDrawable = folderDrawableRL;
		}
		canvas.save();
		canvas.clipPath(path_1);
		canvas.clipPath(path_2, Region.Op.INTERSECT);

		paint.setColorFilter(colorMatrixColorFilter);

		float dis = (float) Math.hypot(yejiaoX - bControl1.x,
				bControl2.y - yejiaoY);
		float f8 = (yejiaoX - bControl1.x) / dis;
		float f9 = (bControl2.y - yejiaoY) / dis;
		matrixArr[0] = 1 - 2 * f9 * f9;
		matrixArr[1] = 2 * f8 * f9;
		matrixArr[3] = matrixArr[1];
		matrixArr[4] = 1 - 2 * f8 * f8;
		matrix.reset();
		matrix.setValues(matrixArr);
		matrix.preTranslate(-bControl1.x, -bControl1.y);
		matrix.postTranslate(bControl1.x, bControl1.y);
		canvas.drawBitmap(bitmap, matrix, paint);

		paint.setColorFilter(null);
		canvas.rotate(degress, bStart1.x, bStart1.y);
		mFolderShadowDrawable.setBounds(left, (int) bStart1.y, right,
				(int) (bStart1.y + maxLength));
		mFolderShadowDrawable.draw(canvas);
		canvas.restore();
	}

	public void computeScroll() {
		super.computeScroll();
		if (scroller.computeScrollOffset()) {
            Log.i("TAG","调用computeScroll动画");
			float x = scroller.getCurrX();
			float y = scroller.getCurrY();
			mTouch.x = x;
			mTouch.y = y;
    //        scrollTo((int)x,(int)y);
            //必须重绘调用onDraw方法
			postInvalidate();
		}
	}

	private void startAnimation(int delayMillis) {
		int dx, dy;

		if (yejiaoX > 0) {
			dx = -(int) (width + mTouch.x);
		} else {
			dx = (int) (width - mTouch.x + width);
		}
		if (yejiaoY > 0) {
			dy = (int) (height - mTouch.y) - 1;
		} else {
			dy = (int) (1 - mTouch.y); // 防止mTouch.y最终变为0
		}

		Log.i("TAG","调用动画");
		scroller.startScroll((int) mTouch.x, (int) mTouch.y, dx, dy,
                delayMillis);

        invalidate();
	}

	public void abortAnimation() {
		if (!scroller.isFinished()) {
			scroller.abortAnimation();
		}
	}

	public boolean canDragOver() {
		if (touch_yejiao_dis > width / 10)
			return true;
		return false;
	}
	public boolean DragToRight() {
		if (yejiaoX > 0)
			return false;
		return true;
	}

}
