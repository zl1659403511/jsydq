package com.example.jsydq.utils;

import com.lidroid.xutils.BitmapUtils;


public class BitmapHelper {
	private BitmapHelper() {
	}

	private static BitmapUtils bitmapUtils;

	/**
	 *            application context
	 * @return
	 */
	public static BitmapUtils getBitmapUtils() {
		if (bitmapUtils == null) {
			// 第二个参数 缓存图片的路径 // 加载图片 最多消耗多少比例的内存 0.05-0.8f
			bitmapUtils = new BitmapUtils(UIUtils.getContext(), FileUtils
					.getIconDir().getAbsolutePath(), 0.3f);
		}
		return bitmapUtils;
	}
}
