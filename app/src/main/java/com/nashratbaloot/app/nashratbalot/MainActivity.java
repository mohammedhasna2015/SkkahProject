package com.nashratbaloot.app.nashratbalot;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.nashratbaloot.app.R;
import com.nashratbaloot.app.nashratbalot.models.MCoupleScores;
import com.nashratbaloot.app.nashratbalot.models.MPoint;
import com.nashratbaloot.app.nashratbalot.utils.DateUtils;
import com.nashratbaloot.app.nashratbalot.utils.FontUtils;
import com.nashratbaloot.app.nashratbalot.widgets.NumberKeyboardView;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnEditorActionListener, OnTouchListener {
	
	public static final String FONT_GEEZA_PRO = "geezapro.ttf";
	public static final String FONT_GEEZA_PRO_BOLD = "geezaprobold.ttf";
	public static final int ANIMATION_DURATION = 250;
	public static final int ANIMATION_UP_DURATION = 400;
	private static final long SECOND_IN_A_WEEK = 7 * 24 * 60 * 60;
	private static final long SECOND_IN_A_MONTH = 30 * 24 * 60 * 60;
	private  Locale currentAppLocale ;
	private TextView tvNameLeft;
	
	private TextView tvNameRight;
	
	private TextView tvScoreLeft;
	
	private TextView tvScoreRight;
	
	private ListView lvScore;
	
	private ImageView ivNavigation;
	
	private Button btnStartNewTurn;
	
	private Button btnCalculate;
	
	private Button btnNewGame;
	
	private ImageButton ibInfo;
	
	private Button btnRevert;
	
	private TextView tvDistribution;
	
	private Button btnCancel;
	
	private Button btnCancelMirror;
	
	private EditText etInputRight;
	
	private EditText etInputRightMirror;
	
	private EditText etInputLeft;
	
	private EditText etInputLeftMirror;
	
	private LinearLayout llAd;
	
	private Button ibRemoveAds;
	
	private NumberKeyboardView numberKeyboardView;
	
	private List<MCoupleScores> listCoupleScores;
	
	private CoupleScoresAdapter adapter;
	
	private boolean isOnInputMode;
	
	private int scoreRight;
	
	private int scoreLeft;
	
	private static int calculateCount;

	private boolean isKeyboardShowing;
	
	private boolean isAboutFragment;

	private AdView adView;
	/**
	 * Listener for handle play buttons animation
	 */
	private AnimationListener animationPlayListener = new AnimationListener() {
		
		@Override public void onAnimationStart(Animation animation) { }
		@Override public void onAnimationRepeat(Animation animation) { }
		
		@Override
		public void onAnimationEnd(Animation animation) {
			if (isOnInputMode) {
				btnStartNewTurn.setVisibility(View.INVISIBLE);
				
				btnCalculate.setVisibility(View.VISIBLE);
				btnCalculate.setClickable(true);
			} else {
				btnStartNewTurn.setVisibility(View.VISIBLE);				
				btnStartNewTurn.setClickable(true);
				
				btnCalculate.setVisibility(View.INVISIBLE);
			}
		}
	};
	
	/**
	 * Listener for handle cancel buttons animation
	 */
	private AnimationListener animationCancelListener = new AnimationListener() {
		
		@Override public void onAnimationStart(Animation animation) { }
		@Override public void onAnimationRepeat(Animation animation) { }
		
		@Override
		public void onAnimationEnd(Animation animation) {
			if (isOnInputMode) {
				btnCancelMirror.setVisibility(View.INVISIBLE);
			
				btnCancel.setVisibility(View.VISIBLE);
				btnCancel.setClickable(true);
				
				ivNavigation.setVisibility(View.INVISIBLE);
				tvDistribution.setVisibility(View.INVISIBLE);
			} else {
				btnCancelMirror.setVisibility(View.VISIBLE);
				
				btnCancel.setVisibility(View.INVISIBLE);
				btnCancel.setClickable(false);
				
				ivNavigation.setVisibility(View.VISIBLE);
			}
		}
	};
	
	/**
	 * Listener for handle input right animation
	 */
	private AnimationListener animationInputRightListener = new AnimationListener() {
		
		@Override public void onAnimationStart(Animation animation) { }
		@Override public void onAnimationRepeat(Animation animation) { }
		
		@Override
		public void onAnimationEnd(Animation animation) {
			if (isOnInputMode) {
				etInputRightMirror.setVisibility(View.INVISIBLE);
				
				etInputRight.setVisibility(View.VISIBLE);
				
				etInputRight.requestFocus();;
				
			} else {
				etInputRightMirror.setVisibility(View.VISIBLE);
				
				etInputRight.setVisibility(View.INVISIBLE);
			}
		}
	};
	
	/**
	 * Listener for handle input left animation
	 */
	private AnimationListener animationInputLeftListener = new AnimationListener() {
		
		@Override public void onAnimationStart(Animation animation) { }
		@Override public void onAnimationRepeat(Animation animation) { }
		
		@Override
		public void onAnimationEnd(Animation animation) {
			if (isOnInputMode) {
				etInputLeftMirror.setVisibility(View.INVISIBLE);
				
				etInputLeft.setVisibility(View.VISIBLE);
				
			} else {
				etInputLeftMirror.setVisibility(View.VISIBLE);
				
				etInputLeft.setVisibility(View.INVISIBLE);
			}
		}
	};
	
	/**
	 * Listener for handle input right up animation
	 */
	private AnimationListener animationInputRightUpListener = new AnimationListener() {
		
		@Override public void onAnimationStart(Animation animation) { }
		@Override public void onAnimationRepeat(Animation animation) { }
		
		@Override
		public void onAnimationEnd(Animation animation) {
			etInputRight.setVisibility(View.INVISIBLE);
		}
	};
	
	/**
	 * Listener for handle input left up animation
	 */
	private AnimationListener animationInputLeftUpListener = new AnimationListener() {
		
		@Override public void onAnimationStart(Animation animation) { }
		@Override public void onAnimationRepeat(Animation animation) { }
		
		@Override
		public void onAnimationEnd(Animation animation) {
			etInputLeft.setVisibility(View.INVISIBLE);
		}
	};
	
	public MainActivity() {
		listCoupleScores = new ArrayList<MCoupleScores>();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

		// Add some null data
		initListScores();
		
		findViews();

		updateResources(MainActivity.this,"en");
		initAdmob();
//		if (!RemoveAdsIapActivity.isPurchased(getBaseContext())) {
//			initAdmob();
//			checkToShowSupportDialog();
//		}
		
		checkToShowFeedbackDialog();
		
		DateUtils.saveCurrentMonthToPref(getBaseContext());
		
	}

	private void initAdmob() {
		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});

			// Create an ad.
			adView = new AdView(this);
			adView.setAdSize(AdSize.SMART_BANNER);
			adView.setAdUnitId("ca-app-pub-9375505665112373/1036714041");

			// TODO add ad view to layout
			llAd.addView(adView);

			AdRequest adRequest = new AdRequest.Builder().build();

			// Start loading the ad in the background.
			adView.loadAd(adRequest);

			adView.setVisibility(View.GONE);
			adView.setAdListener(new AdListener() {
				@Override
				public void onAdFailedToLoad(int errorCode) {
					super.onAdFailedToLoad(errorCode);
					Log.e("onAdOpened", "onAdOpened");
					adView.setVisibility(View.GONE);
					ibRemoveAds.setVisibility(View.GONE);
				}

				@Override
				public void onAdLoaded() {
					super.onAdLoaded();
					adView.setVisibility(View.VISIBLE);
					ibRemoveAds.setVisibility(View.VISIBLE);
					Log.e("onAdOpened", "onAdOpened");
				}

				@Override
				public void onAdOpened() {
					super.onAdOpened();
					adView.setVisibility(View.VISIBLE);
					ibRemoveAds.setVisibility(View.VISIBLE);
					Log.e("onAdOpened", "onAdOpened");
				}
			});

		}

//	private void checkToShowSupportDialog() {
//		if (!DateUtils.getCurrentMonth(getBaseContext()).equals(DateUtils.getCurrentMonth())) {
//			showADialog(getString(R.string.support_title), getString(R.string.support_content), getString(R.string.support_buy),
//					new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					requestPurchase();
//				}
//			});
//		}
//	}
	
	private void checkToShowFeedbackDialog() {
		if (DateUtils.isFeedbackClicked(getBaseContext()))
			return;
		
		if (DateUtils.getFirstTime(this)  == 0) {
			DateUtils.saveFirstTimeToPref(this);
			return;
		}

		if ((System.currentTimeMillis() / 1000L)
				- DateUtils.getFirstTime(getBaseContext()) >= SECOND_IN_A_WEEK) {
			if (!DateUtils.is7DaysPassed(getBaseContext())) {
				showFeedbackDialog();
				DateUtils.saveIs7DaysPassed(getBaseContext(), true);

			} else {
				if ((System.currentTimeMillis() / 1000L)
						- DateUtils.getFirstTime(getBaseContext()) >= SECOND_IN_A_MONTH) {
					showFeedbackDialog();
				}
			}

		}
	}
	
	private void showFeedbackDialog() {
		DateUtils.saveFirstTimeToPref(this);
		showADialog(getString(R.string.feedback_title), getString(R.string.feedback_content), getString(R.string.feedback_positive), getString(R.string.feedback_negative),
			new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					DateUtils.saveIsFeedBackClicked(getBaseContext(), true);
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getBaseContext().getPackageName()));
					startActivity(browserIntent);
				}
			});
	}

	private void findViews() {
		tvNameLeft = (TextView) findViewById(R.id.tvNameLeft);
		tvNameRight = (TextView) findViewById(R.id.tvNameRight);
		tvScoreLeft = (TextView) findViewById(R.id.tvScoreLeft);
		tvScoreRight = (TextView) findViewById(R.id.tvScoreRight);
		ivNavigation = (ImageView) findViewById(R.id.ivNavigation);
		btnStartNewTurn = (Button) findViewById(R.id.btnStartNewTurn);
		btnCalculate = (Button) findViewById(R.id.btnCalculate);
		btnNewGame = (Button) findViewById(R.id.btnNewGame);
		ibInfo = (ImageButton) findViewById(R.id.ibInfo);
		lvScore = (ListView) findViewById(R.id.lvScore);
		btnRevert = (Button) findViewById(R.id.btnRevert);
		tvDistribution = (TextView) findViewById(R.id.tvDistribution);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancelMirror = (Button) findViewById(R.id.btnCancelMirror);
		etInputRight = (EditText) findViewById(R.id.etInputRight);
		etInputRightMirror = (EditText) findViewById(R.id.etInputRightMirror);
		etInputLeft = (EditText) findViewById(R.id.etInputLeft);
		etInputLeftMirror = (EditText) findViewById(R.id.etInputLeftMirror);
		
		llAd = (LinearLayout) findViewById(R.id.llAd);
		ibRemoveAds = (Button) findViewById(R.id.ibRemoveAds);
		numberKeyboardView = (NumberKeyboardView) findViewById(R.id.viewKeyboard);
		
		tvNameLeft.setTypeface(FontUtils.get(getBaseContext(), FONT_GEEZA_PRO));
		tvNameRight.setTypeface(FontUtils.get(getBaseContext(), FONT_GEEZA_PRO));
		tvDistribution.setTypeface(FontUtils.get(getBaseContext(), FONT_GEEZA_PRO));
		btnStartNewTurn.setTypeface(FontUtils.get(getBaseContext(), FONT_GEEZA_PRO_BOLD));
		btnCalculate.setTypeface(FontUtils.get(getBaseContext(), FONT_GEEZA_PRO_BOLD));
		btnRevert.setTypeface(FontUtils.get(getBaseContext(), FONT_GEEZA_PRO));
		btnNewGame.setTypeface(FontUtils.get(getBaseContext(), FONT_GEEZA_PRO));
		
		btnStartNewTurn.setOnClickListener(this);
		btnCalculate.setOnClickListener(this);
		btnNewGame.setOnClickListener(this);
		ibInfo.setOnClickListener(this);
		btnRevert.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		ivNavigation.setOnClickListener(this);
		
		etInputRight.setOnEditorActionListener(this);
		etInputLeft.setOnEditorActionListener(this);
		ibRemoveAds.setOnClickListener(this);
		
		etInputRight.setOnTouchListener(this);
		etInputLeft.setOnTouchListener(this);
		etInputRightMirror.setOnTouchListener(this);
		etInputLeftMirror.setOnTouchListener(this);
		
		btnStartNewTurn.setShadowLayer(1, 0, 0, Color.BLACK);
		
		etInputRight.addTextChangedListener(new TextWatcher() {
			
			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() == 2) {
					if (!TextUtils.isEmpty(etInputLeft.getText().toString())) {
						onCalculate();
						return;
					}
					
					etInputLeft.requestFocus();
				}
			}
		});
		
		adapter = new CoupleScoresAdapter(this, R.layout.item_couple_scores, listCoupleScores);
		
		lvScore.setAdapter(adapter);
		
		adapter.notifyDataSetChanged();
		lvScore.post(new Runnable(){
			  public void run() {
				  lvScore.setSelection(lvScore.getCount() - 1);
		  }});
		
		numberKeyboardView.setCallback(new NumberKeyboardView.NumberKeyboardCallback() {
			
			@Override
			public void onClick(String number) {
				if (!TextUtils.isEmpty(number)) {
					if (etInputLeft.isFocused()) {
						etInputLeft.getText().insert(etInputLeft.getSelectionStart(), number);
					} else if (etInputRight.isFocused()) {
						etInputRight.getText().insert(etInputRight.getSelectionStart(), number);
					}
					
				} else {
					if (etInputLeft.isFocused() && !TextUtils.isEmpty(etInputLeft.getText().toString())) {
						etInputLeft.getText().delete(etInputLeft.getSelectionStart() - 1, etInputLeft.getSelectionStart());
					} else if (etInputRight.isFocused() && !TextUtils.isEmpty(etInputRight.getText().toString())) {
						etInputRight.getText().delete(etInputRight.getSelectionStart() - 1, etInputRight.getSelectionStart());
					}
				}
			}
		});
		
		getBottomHeight(btnStartNewTurn);
	}
	
	private void initListScores() {
		listCoupleScores.clear();
		for (int i = 0; i < 10; i++) {
			listCoupleScores.add(null);	
		}
	}
	
	/**
	 * Adapter for display user's scores
	 * @author May
	 *
	 */
	class CoupleScoresAdapter extends ArrayAdapter<MCoupleScores> {

        private Context context;
        private int resource;
        private List<MCoupleScores> listCoupleScores;

        public CoupleScoresAdapter(Context context, int resource, List<MCoupleScores> objects) {
            super(context, resource, objects);

            this.context = context;
            this.resource = resource;
            this.listCoupleScores = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

        	MCoupleScores itemCoupleScores = listCoupleScores.get(position);
        	CoupleScoresHolder holder;

            if (convertView == null) {
                // Initialize instances
                convertView = LayoutInflater.from(context).inflate(resource, parent, false);
                holder = new CoupleScoresHolder();

                // TODO Find views for every single item
                holder.tvScoreLeft = (TextView) convertView.findViewById(R.id.tvScoreLeft);
                holder.tvScoreRight = (TextView) convertView.findViewById(R.id.tvScoreRight);

                convertView.setTag(holder);
            } else {
                holder = (CoupleScoresHolder) convertView.getTag();
            }

            // Fill views
            if (itemCoupleScores != null) {
            	holder.tvScoreLeft.setText(itemCoupleScores.getScoreLeft()+"");
            	holder.tvScoreRight.setText(itemCoupleScores.getScoreRight()+"");

            } else {
            	holder.tvScoreLeft.setText("");
            	holder.tvScoreRight.setText("");
            }
            
            

            return convertView;
        }

        class CoupleScoresHolder {
            // Holder for CoupleScoresAdapter
        	TextView tvScoreLeft;
        	TextView tvScoreRight;
        }
    }

	@Override
	public void onClick(View v) {
		if (v == ibInfo) {
			isAboutFragment = true;
			setClickable(false);
			getSupportFragmentManager().beginTransaction()
			.setCustomAnimations(
				R.anim.slide_in,
				R.anim.slide_out,
				R.anim.slide_in,
				R.anim.slide_out)
			.add(R.id.container, new AboutFragment()).addToBackStack(null).commitAllowingStateLoss();
			
		} else if (v == btnNewGame) {
			showADialog(getString(R.string.title_new_game), "", getString(R.string.btn_new_game), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startNewGame();
				}
			});
			
		} else if (v == btnStartNewTurn) {
			showKeyboard(true);
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					if (getTopHeight(numberKeyboardView) >= getBottomHeight(btnStartNewTurn)) {
						RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btnCalculate.getLayoutParams();
						params.addRule(RelativeLayout.ABOVE, R.id.btnStartNewTurn);
						btnCalculate.setLayoutParams(params);
						
					} else {
						RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) btnCalculate.getLayoutParams();
						params.addRule(RelativeLayout.ABOVE, 0);
						btnCalculate.setLayoutParams(params);
					}
					
					new Handler().postDelayed(new Runnable() {
						
						@Override
						public void run() {
							ivNavigation.setVisibility(View.GONE);
							tvDistribution.setVisibility(View.GONE);
							toggleViews(true);
						}
					}, 100);
					
				}
			}, 100);
			
		} else if (v == btnCalculate) {
			onCalculate();
			
		} else if (v == btnRevert) {
			try {
				listCoupleScores.remove(listCoupleScores.size() - 1);
				adapter.notifyDataSetChanged();
				updateScore();
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
			
		} else if (v == btnCancel) {
			showKeyboard(false);
			toggleViews(false);
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					moveInputRight(false);
					moveInputLeft(false);
				}
			}, 200);
			
		} else if (v == ivNavigation) {
			spinArrow();
			
		} else if (v == ibRemoveAds) {
			adView.setVisibility(View.GONE);
			ibRemoveAds.setVisibility(View.GONE);
		}
	}

	private void showKeyboard(boolean isShow) {
		if (isShow) {
			isKeyboardShowing = true;
			numberKeyboardView.setVisibility(View.VISIBLE);
		} else {
			isKeyboardShowing = false;
			numberKeyboardView.setVisibility(View.GONE);
		}
	}
	
	private void onCalculate() {
		showKeyboard(false);
		spinArrow();
		
		if (TextUtils.isEmpty(etInputRight.getText().toString())) {
			etInputRight.requestFocus();
			return;
		}
		
		if (TextUtils.isEmpty(etInputLeft.getText().toString())) {
			etInputLeft.requestFocus();
			return;
		}
				
		// Add data to the list
		listCoupleScores.add(new MCoupleScores(
				Integer.parseInt(etInputLeft.getText().toString()),
				Integer.parseInt(etInputRight.getText().toString())));
		
		adapter.notifyDataSetChanged();
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				scrollToEnd();
			}
		}, 300);

		updateScore();
		
		// Start animations
		toggleViews(false);
		flyOutInputRight();
		flyOutInputLeft();		
	}
	
	private void scrollToEnd() {
		lvScore.smoothScrollToPosition(listCoupleScores.size() - 1);
	}
	
	private void spinArrow() {
		calculateCount++;
		if (calculateCount %4 == 0) {
			ivNavigation.setImageResource(R.drawable.icon_right);
		} else if (calculateCount %4 == 1) {
			ivNavigation.setImageResource(R.drawable.icon_up);
		} else if (calculateCount % 4 == 2) {
			ivNavigation.setImageResource(R.drawable.icon_left);
		} else if (calculateCount % 4 == 3) {
			ivNavigation.setImageResource(R.drawable.icon_down);
		}
	}
	
	private void updateScore() {
		scoreLeft = 0;
		scoreRight = 0;
		for (MCoupleScores scores: listCoupleScores) {
			if (scores != null) {
				scoreLeft = scoreLeft + scores.getScoreLeft();
				scoreRight = scoreRight + scores.getScoreRight();
			}
		}
		
		tvScoreLeft.setText(scoreLeft+"");
		tvScoreRight.setText(scoreRight+"");
		
		if (scoreLeft >= 152 && scoreRight < 152) {
			showWinnerDialog(getString(R.string.message_left_win));
			
		} else if (scoreLeft < 152 && scoreRight >= 152) {
			showWinnerDialog(getString(R.string.message_right_win));
			
		} else if (scoreLeft >= 152 && scoreRight >= 152) {
			if (scoreLeft == scoreRight) {
//				showWinnerDialog("draw");
				
			} else if (scoreLeft > scoreRight) {
				showWinnerDialog(getString(R.string.message_left_win));
				
			} else {
				showWinnerDialog(getString(R.string.message_right_win));
			}
		}
	}
	
	private void showWinnerDialog(String winner) {
		showADialog(getString(R.string.title_dialog), winner, getString(R.string.btn_new_game), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				startNewGame();
			}
		});
	}
	
	private void startNewGame() {
		// TODO
		initListScores();
		updateScore();
		adapter.notifyDataSetChanged();
	}
	
	public void toggleViews(boolean isExpand) {
		isOnInputMode = isExpand;

		if (isExpand) {
			moveCancelButton(true);
			movePlayButton(true);
			moveInputRight(true);
			moveInputLeft(true);
		} else {
//			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//			imm.hideSoftInputFromWindow(etInputRight.getWindowToken(), 0);
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					moveCancelButton(false);
					movePlayButton(false);
				}
			}, 200);
		}

	}
	
	/**
	 * Move the play button up or down
	 * @param isMoveUp
	 */
	private void movePlayButton(boolean isMoveUp) {
		if (isMoveUp) {
			btnStartNewTurn.setClickable(false);
			
			TranslateAnimation animUp = new TranslateAnimation(0, 0, 0, 
					getViewPosition(btnCalculate).getPositionY() - getViewPosition(btnStartNewTurn).getPositionY());
			animUp.setDuration(ANIMATION_DURATION);
			animUp.setAnimationListener(animationPlayListener);
			
			btnStartNewTurn.startAnimation(animUp);
			
		} else {
			btnCalculate.setClickable(false);
			
//			ivNavigation.setVisibility(View.VISIBLE);
			tvDistribution.setVisibility(View.VISIBLE);
			
			TranslateAnimation animDown = new TranslateAnimation(0, 0, 0,
					getViewPosition(btnStartNewTurn).getPositionY() - getViewPosition(btnCalculate).getPositionY());
			animDown.setDuration(ANIMATION_DURATION);
			animDown.setAnimationListener(animationPlayListener);
			
			btnCalculate.startAnimation(animDown);
			
		}
	}
	
	private void moveCancelButton(boolean isMoveUp) {
		if (isMoveUp) {
			btnCancelMirror.setVisibility(View.INVISIBLE);
			btnCancelMirror.setClickable(false);
			
			TranslateAnimation animUp = new TranslateAnimation(0, 0, 0, 
					getViewPosition(btnCancel).getPositionY() - getViewPosition(btnCancelMirror).getPositionY());
			animUp.setDuration(ANIMATION_DURATION);
			animUp.setAnimationListener(animationCancelListener);
			
			btnCancelMirror.startAnimation(animUp);
			
		} else {
			btnCancel.setVisibility(View.INVISIBLE);
			btnCancel.setClickable(false);
			
			TranslateAnimation animDown = new TranslateAnimation(0, 0, 0,
					getViewPosition(btnCancelMirror).getPositionY() - getViewPosition(btnCancel).getPositionY());
			animDown.setDuration(ANIMATION_DURATION);
			animDown.setAnimationListener(animationCancelListener);
			
			btnCancel.startAnimation(animDown);
			
		}
	}
	
	private void moveInputRight(boolean isMoveUp) {
		if (isMoveUp) {
			etInputRightMirror.setVisibility(View.INVISIBLE);
			etInputRight.setText("");
			
			TranslateAnimation animUp = new TranslateAnimation(
					0, getViewPosition(etInputRight).getPositionX() - getViewPosition(etInputRightMirror).getPositionX(),
					0, getViewPosition(etInputRight).getPositionY() - getViewPosition(etInputRightMirror).getPositionY());
			animUp.setDuration(ANIMATION_DURATION);
			animUp.setAnimationListener(animationInputRightListener);
			
			etInputRightMirror.startAnimation(animUp);
			
		} else {
			etInputRight.setVisibility(View.INVISIBLE);
			
			TranslateAnimation animDown = new TranslateAnimation(
					0, getViewPosition(etInputRightMirror).getPositionX() - getViewPosition(etInputRight).getPositionX(),
					0, getViewPosition(etInputRightMirror).getPositionY() - getViewPosition(etInputRight).getPositionY());
			animDown.setDuration(ANIMATION_DURATION);
			animDown.setAnimationListener(animationInputRightListener);
			
			etInputRight.startAnimation(animDown);
			
		}
	}
	
	private void moveInputLeft(boolean isMoveUp) {
		if (isMoveUp) {
			etInputLeftMirror.setVisibility(View.INVISIBLE);
			etInputLeft.setText("");
			
			TranslateAnimation animUp = new TranslateAnimation(
					0, getViewPosition(etInputLeft).getPositionX() - getViewPosition(etInputLeftMirror).getPositionX(),
					0, getViewPosition(etInputLeft).getPositionY() - getViewPosition(etInputLeftMirror).getPositionY());
			animUp.setDuration(ANIMATION_DURATION);
			animUp.setAnimationListener(animationInputLeftListener);
			
			etInputLeftMirror.startAnimation(animUp);
			
		} else {
			etInputLeft.setVisibility(View.INVISIBLE);
			
			TranslateAnimation animDown = new TranslateAnimation(
					0, getViewPosition(etInputLeftMirror).getPositionX() - getViewPosition(etInputLeft).getPositionX(),
					0, getViewPosition(etInputLeftMirror).getPositionY() - getViewPosition(etInputLeft).getPositionY());
			animDown.setDuration(ANIMATION_DURATION);
			animDown.setAnimationListener(animationInputLeftListener);
			
			etInputLeft.startAnimation(animDown);
			
		}
	}
	
	private void flyOutInputRight() {
		TranslateAnimation animUp = new TranslateAnimation(
				0, 0,
				0, - getViewPosition(etInputRight).getPositionY());
		animUp.setDuration(ANIMATION_UP_DURATION);
		animUp.setAnimationListener(animationInputRightUpListener);
		
		etInputRight.startAnimation(animUp);
	}
	
	private void flyOutInputLeft() {
		TranslateAnimation animUp = new TranslateAnimation(
				0, 0,
				0, - getViewPosition(etInputLeft).getPositionY());
		animUp.setDuration(ANIMATION_UP_DURATION);
		animUp.setAnimationListener(animationInputLeftUpListener);
		
		etInputLeft.startAnimation(animUp);
	}
	
	/**
	 * Get 
	 * @param view
	 * @return
	 */
	private MPoint getViewPosition(View view) {
		int position[] = new int[2];
		view.getLocationOnScreen(position);
		return new MPoint(position[0], position[1]);
	}
	
	@SuppressWarnings("deprecation")
	private int getBottomHeight(View view) {	
		Rect rect = new Rect();
		view.getGlobalVisibleRect(rect);

		Display display = getWindowManager().getDefaultDisplay(); 
		int height = display.getHeight();
		
		return (height - rect.bottom);
	}
	
	@SuppressWarnings("deprecation")
	private int getTopHeight(View view) {	
		Rect rect = new Rect();
		view.getGlobalVisibleRect(rect);

		Display display = getWindowManager().getDefaultDisplay(); 
		int height = display.getHeight();
		
		return (height - rect.top);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			if (v == etInputRight) {
	        	  etInputLeft.requestFocus();
	          } else if (v == etInputLeft) {
	        	  onCalculate();
	          }
			return true;
		}
		return false;
	}
	
	private void showADialog(String title, String message, String positive, DialogInterface.OnClickListener onPositiveClick) {
		new AlertDialog.Builder(this)
	    .setTitle(title)
	    .setMessage(message)
	    .setPositiveButton(positive, onPositiveClick)
	    .setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	scrollToEnd();
	        }
	     }).setCancelable(false)
	    .show();
	}
	
	private void showADialog(String title, String message, String positive, String negative, DialogInterface.OnClickListener onPositiveClick) {
		new AlertDialog.Builder(this)
	    .setTitle(title)
	    .setMessage(message)
	    .setPositiveButton(positive, onPositiveClick)
	    .setNegativeButton(negative, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	scrollToEnd();
	        }
	     }).setCancelable(false)
	    .show();
	}
	
	@SuppressLint("NewApi")
	private void hideStatusBar() {
		if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
        	View decorView = getWindow().getDecorView();
        	// Hide the status bar.
        	int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        	decorView.setSystemUiVisibility(uiOptions);
        }
	}
		
	private String getDeviceId() {
		String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
		return md5(android_id).toUpperCase();
	}
	
	public String md5(String s) {
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = MessageDigest.getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        // Create Hex String
	        StringBuffer hexString = new StringBuffer();
	        for (int i=0; i<messageDigest.length; i++)
	            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	        return hexString.toString();

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	


	
	@Override
	public void onBackPressed() {
		if (isKeyboardShowing) {
			showKeyboard(false);
		} else {
			if (isAboutFragment) {
				isAboutFragment = false;
				setClickable(true);
			}
			super.onBackPressed();	
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v == etInputRight) {
			etInputRight.requestFocus();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(etInputRight.getWindowToken(), 0);
			if (!isKeyboardShowing) showKeyboard(true);
			
		} else if (v == etInputLeft) {
			etInputLeft.requestFocus();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(etInputLeft.getWindowToken(), 0);
			if (!isKeyboardShowing) showKeyboard(true);
			
		}
		
		return true;
	}
	
	public interface GetKeyboardHeightListener {
		
		public void onComplete(int height);
		
	}

	private  void updateResources(Context context, String language) {
		currentAppLocale = new Locale(language);
		Resources res=context.getResources();
		DisplayMetrics dm =res.getDisplayMetrics();
		Configuration conf = context.getResources().getConfiguration();
		conf.locale= currentAppLocale;

		res.updateConfiguration(conf,dm);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			conf.setLayoutDirection(currentAppLocale);
		}

		res.updateConfiguration(conf,dm);
	}

	public void setClickable(boolean clickable) {
		btnStartNewTurn.setClickable(clickable);
		btnCalculate.setClickable(clickable);
		btnNewGame.setClickable(clickable);
		ibInfo.setClickable(clickable);
		btnRevert.setClickable(clickable);
		btnCancel.setClickable(clickable);
		ivNavigation.setClickable(clickable);
	}
}
