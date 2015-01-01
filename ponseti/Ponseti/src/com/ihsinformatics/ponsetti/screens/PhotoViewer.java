package com.ihsinformatics.ponsetti.screens;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.utils.BitmapHolder;
import com.ihsinformatics.ponsetti.utils.exceptions.BitmapConsumedException;

public class PhotoViewer extends Activity {

	ImageView imageView;
	WebView webView;
	private static List<Bitmap> BITMAPS_LIST;
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_viewer);
		
		

		// Intent intent = getIntent();
		BitmapHolder bitmapHolder = BitmapHolder.getinstance(); 
		// Bitmap bitmap = null;
		BITMAPS_LIST = null;
		try {
			BITMAPS_LIST = bitmapHolder.consumeBitmaps(getClass().getName());
		} catch (BitmapConsumedException e) {
			e.printStackTrace();
		}
		if (BITMAPS_LIST != null) {
			mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			mViewPager.setCurrentItem(bitmapHolder.getClickedItemIndex(), true);
		}

	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			return PlaceholderFragment.newInstance(position);
		}

		@Override
		public int getCount() {
			
			return BITMAPS_LIST.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			
			return null;
		}
	}

	
	public static class PlaceholderFragment extends Fragment {
		
		private static final String BITMAP_INDEX = "bitmap_index";
		ImageView imageView;

		
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(BITMAP_INDEX, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_image_viewer, container, false);
			imageView = (ImageView) rootView.findViewById(R.id.ivMain);
			Bitmap bitmap = BITMAPS_LIST.get(getArguments().getInt(BITMAP_INDEX));
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			if (width > height) {

				Matrix matrix = new Matrix();
				matrix.postRotate(90);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

			}
			
			Drawable drawable = new BitmapDrawable(getResources(), bitmap);
			imageView.setImageDrawable(drawable);
			return rootView;
		}
	}

}
