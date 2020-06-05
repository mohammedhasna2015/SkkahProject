package com.nashratbaloot.app.nashratbalot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nashratbaloot.app.R;
import com.nashratbaloot.app.nashratbalot.utils.FontUtils;

@SuppressLint("NewApi")
public class AboutFragment extends Fragment implements OnClickListener {
	public static final String TAG = "AboutActivity";

	
	private Button btnBack;
	private RelativeLayout whatsappRelativeLayout,emailRelativeLayout;
	private TextView textView10;
	private ImageView snapImageView,twitterImageView,instegramImageView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_about, container, false);

	    btnBack = (Button) view.findViewById(R.id.btnBack);
		textView10=(TextView)view.findViewById(R.id.textView10);
		btnBack = (Button) view.findViewById(R.id.btnBack);
		emailRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_email);
		whatsappRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_whatsapp);
		twitterImageView = (ImageView) view.findViewById(R.id.image_twitter_media);
		snapImageView = (ImageView) view.findViewById(R.id.image_snap_chat_media);
		instegramImageView = (ImageView) view.findViewById(R.id.image_instgram_app_media);
		

		btnBack.setOnClickListener(this);
		whatsappRelativeLayout.setOnClickListener(this);
		emailRelativeLayout.setOnClickListener(this);
		snapImageView.setOnClickListener(this);
		twitterImageView.setOnClickListener(this);
		instegramImageView.setOnClickListener(this);


		try {
			String versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
			textView10.setText(versionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return view;
	}
	


	@Override
	public void onClick(View v) {
		if (v == btnBack) {
			getActivity().onBackPressed();
		}
		if (v==whatsappRelativeLayout){
			sendUserToLink("https://wa.me/966507955755");
		}
		if (v==emailRelativeLayout){
			sendEmail("Sakkah_ar@outlook.com");
		}

		if (v==twitterImageView){
			sendUserToLink("https://mobile.twitter.com/skkah_ar?locale=ar");
		}

		if (v==snapImageView){
			sendUserToLink("http://www.snapchat.com/add/Skkah_Ar");
		}
		if (v==instegramImageView){
			sendUserToLink("https://www.instagram.com/skkah_ar/");
		}
	}

	public void sendUserToLink(String url){
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

	public void sendEmail(String address) {
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
	            "mailto", address, null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From Lna Lhm user");
		startActivity(Intent.createChooser(emailIntent, "Send email to "+address));
	}
}
