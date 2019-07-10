package com.blockadm.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blockadm.common.R;
import com.blockadm.common.utils.StringUtils;


/**
 * 
 ************************************************** 
 * @ClassName: CommonDialog
 * @Description: TODO(自定义风格dialog)
 * @author 唐宏宇
 * @date 2014年10月30日 下午4:05:54
 * 
 *************************************************** 
 */
public class CommonDialog extends Dialog implements DialogInterface {

	public CommonDialog(Context context) {
		super(context);

	}

	public CommonDialog(Context context, int theme) {
		super(context, theme);

	}
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
//		setCanceledOnTouchOutside(true);
	}



	public static class Builder {
		private Context mContext;
		// 设置能否退出
		private Boolean isCancelable = true;

		// 对话框内容
		private View contentView;

		// 提示信息
		private String mDialogPrompte;

		// 对话框标题
		private String mDialogTitle;

		// 对话框标题
		private String dialogWarning;

		// 对话框图标
		private int mDialogIcon;

		// 对话框信息
		private String mDialogMessage;
		// 第一个按钮
		private String mPositiveBtnText;
		// 第二个按钮
		private String nNeturalBtnText;

		private String mNegativeBtnText;

		private View.OnClickListener onClickListener;

		private OnClickListener mPositiveBtnListener,
				mNetrualBtnListener, mNegativeBtnListener;
		private OnCancelListener mCancelListener;
		private OnDismissListener mDismissListener;
		private int mTitleColor;
		private int mTitleSize;
		private int mMessageColor;
		private int mMessageSize;

		public Builder(Context context) {
			this.mContext = context;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public Builder setIcon(int imageID) {
			this.mDialogIcon = imageID;
			return this;
		}

		public Builder setTitle(String title) {
			this.mDialogTitle = title;
			return this;
		}

		public Builder setPromote(String promote) {
			this.mDialogPrompte = promote;
			return this;
		}

		public Builder setPromote(int promote) {
			this.mDialogPrompte = mContext.getString(promote);
			return this;
		}

		public String getDialogWarning() {
			return dialogWarning;
		}

		public Builder setDialogWarning(String dialogWarning) {
			this.dialogWarning = dialogWarning;
			return this;
		}

		public Builder setDialogWarning(int dialogWarning) {
			this.dialogWarning = mContext.getString(dialogWarning);
			return this;
		}

		public Builder setTitle(int title) {
			this.mDialogTitle = mContext.getString(title);
			return this;
		}

		public Builder setMessage(String message) {
			this.mDialogMessage = message;
			return this;
		}

		public Builder setMessage(int message) {
			this.mDialogMessage = mContext.getString(message);
			return this;
		}

		public Builder setTitleColor(int color) {
			this.mTitleColor = color;
			return this;
		}
		public Builder setTitleSize(int size) {
			this.mTitleSize = size;
			return this;
		}
		public Builder setMessageColor(int color) {
			this.mMessageColor = color;
			return this;
		}
		public Builder setMessageSize(int size) {
			this.mMessageSize = size;
			return this;
		}

		public Builder setOnDialogWaringClickListener(
				View.OnClickListener onClickListener) {
			this.onClickListener = onClickListener;
			return this;
		}

		public Builder setCancelable(boolean flag) {
			this.isCancelable = flag;
			return this;
		}

		public Builder setPositiveButton(String mPositiveBtnText,
                                         OnClickListener mPositiveBtnListener) {
			this.mPositiveBtnText = mPositiveBtnText;
			this.mPositiveBtnListener = mPositiveBtnListener;
			return this;
		}

		//
		// public Builder setNeutralButton(String SecondButtonText,
		// DialogInterface.OnClickListener SecondButtonListener) {
		// this.nNeturalBtnText = SecondButtonText;
		// this.mNetrualBtnListener = SecondButtonListener;
		// return this;
		// }

		public Builder setPositiveButton(int mPositiveBtnText,
				OnClickListener mPositiveBtnListener) {
			this.mPositiveBtnText = mContext.getString(mPositiveBtnText);
			this.mPositiveBtnListener = mPositiveBtnListener;
			return this;
		}

		// public Builder setNeutralButton(int SecondButtonText,
		// DialogInterface.OnClickListener SecondButtonListener) {
		// this.nNeturalBtnText = mContext.getString(SecondButtonText);
		// this.mNetrualBtnListener = SecondButtonListener;
		// return this;
		// }

		public Builder setNegativeButton(String mNegativeBtnText,
                                         OnClickListener mNegativeBtnListener) {
			this.mNegativeBtnText = mNegativeBtnText;
			this.mNegativeBtnListener = mNegativeBtnListener;
			return this;
		}

		public Builder setNegativeButton(int mNegativeBtnText,
				OnClickListener mNegativeBtnListener) {
			this.mNegativeBtnText = mContext.getString(mNegativeBtnText);
			this.mNegativeBtnListener = mNegativeBtnListener;
			return this;
		}

		public void setOnCancelListener(
				OnCancelListener cancelListener) {
			this.mCancelListener = cancelListener;
		}

		public void setOnDismissListener(
				OnDismissListener dismissListener) {
			mDismissListener = dismissListener;
		}

		/**
		 * 
		 * @Description 给dialog设置内容
		 * @param
		 * @return
		 */
		public CommonDialog create() {

			
			WindowManager wm = (WindowManager) mContext
					.getSystemService(Context.WINDOW_SERVICE);
			int width = wm.getDefaultDisplay().getWidth();
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final CommonDialog dialog = new CommonDialog(mContext,
					R.style.dialog_base);
			// 设置样式
			View layout = inflater.inflate(R.layout.dialog_common_blockadm, null);
			dialog.addContentView(layout, new LayoutParams(width / 5 * 4,
					LayoutParams.WRAP_CONTENT));

			TextView title = (TextView) layout.findViewById(R.id.dialog_title);
			TextView tvDialogWarning = (TextView) layout
					.findViewById(R.id.tv_dialog_warning);
			ImageView icon = (ImageView) layout.findViewById(R.id.dialog_icon);
			TextView prompte = (TextView) layout
					.findViewById(R.id.dialog_prompte);

			Button positivieBtn = (Button) layout
					.findViewById(R.id.btn_positive);
			// Button SecondButton = (Button) layout
			// .findViewById(R.id.dialog_but2);
			Button negativeBtn = (Button) layout
					.findViewById(R.id.btn_negative);
			// 判断提示信息
			if (!TextUtils.isEmpty(mDialogPrompte)) {
				prompte.setVisibility(View.VISIBLE);
				prompte.setText(mDialogPrompte);
			} else {
				prompte.setVisibility(View.GONE);
			}

			if (StringUtils.isEmpty(dialogWarning)) {
				tvDialogWarning.setVisibility(View.GONE);

			} else {
				tvDialogWarning.setText(dialogWarning);
			}

			tvDialogWarning.setOnClickListener(onClickListener);

			// 设置能否返回键退出
			if (!isCancelable) {
				dialog.setCancelable(isCancelable);
			}

			if (!TextUtils.isEmpty(mDialogTitle)) {
				title.setVisibility(View.VISIBLE);
				title.setText(mDialogTitle);
			} else {
				title.setVisibility(View.GONE);
			}

			if (mTitleColor!=0) {
				title.setTextColor(mTitleColor);//设置标题的文字颜色
			}
			if(mTitleSize!=0){
				title.setTextSize(TypedValue.COMPLEX_UNIT_SP,mTitleSize);//设置标题的文字大小
			}
			if (mMessageColor!=0) {
				prompte.setTextColor(mMessageColor);//设置提示内容颜色
			}
			if(mMessageSize!=0){
				prompte.setTextSize(TypedValue.COMPLEX_UNIT_SP,mMessageSize);//设置提示内容大小
			}

			// 判断标题
			if (mDialogIcon != 0) {
				icon.setImageResource(mDialogIcon);

			} else {

				icon.setVisibility(View.GONE);
			}

			if (!TextUtils.isEmpty(mPositiveBtnText)) {
				positivieBtn.setText(mPositiveBtnText);
				if (mPositiveBtnListener != null) {
					positivieBtn.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {

							mPositiveBtnListener.onClick(dialog,
									DialogInterface.BUTTON_POSITIVE);
						}

					});
				}

			} else {
				positivieBtn.setVisibility(View.GONE);
			}

			// if (nNeturalBtnText != null && !nNeturalBtnText.equals("")) {
			// SecondButton.setText(nNeturalBtnText);
			// if (mNetrualBtnListener != null) {
			// SecondButton.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			//
			// mNetrualBtnListener.onClick(dialog,
			// DialogInterface.BUTTON_NEGATIVE);
			// }
			//
			// });
			// }
			//
			// } else {
			// SecondButton.setVisibility(View.GONE);
			// }

			if (!TextUtils.isEmpty(mNegativeBtnText)) {
				negativeBtn.setText(mNegativeBtnText);
				if (mNegativeBtnListener != null) {
					negativeBtn.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {

							mNegativeBtnListener.onClick(dialog,
									DialogInterface.BUTTON_NEGATIVE);
						}

					});
				}

			} else {
				negativeBtn.setVisibility(View.GONE);
			}
			// 判断有没有按钮，如果没有底部将消失
			if (mPositiveBtnText == null && nNeturalBtnText == null
					&& mNegativeBtnText == null) {

				 layout
						.findViewById(R.id.ll_dialog_bottom).setVisibility(View.GONE);
			}

			// 判断Dialog内容或者加进去的View是否存在
			TextView message = (TextView) layout
					.findViewById(R.id.dialog_message);

			if (!TextUtils.isEmpty(mDialogMessage)) {

				message.setText(mDialogMessage);

			} else {
				message.setVisibility(View.GONE);
			}

			if (TextUtils.isEmpty(mDialogTitle)
					&& TextUtils.isEmpty(mDialogPrompte) && mDialogIcon == 0) {

				layout.findViewById(R.id.ll_dialog_title).setVisibility(
						View.GONE);
				layout.findViewById(R.id.view_line).setVisibility(View.GONE);

			}

			if (contentView != null) {
				LinearLayout dialogContent = (LinearLayout) layout
						.findViewById(R.id.dialog_content);

				dialogContent.addView(contentView, new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			}
			if (mCancelListener != null) {
				dialog.setOnCancelListener(this.mCancelListener);
			}
			if (mDismissListener != null) {
				dialog.setOnDismissListener(mDismissListener);
			}

			dialog.setContentView(layout);
			return dialog;
		}

	}
	
	

}
