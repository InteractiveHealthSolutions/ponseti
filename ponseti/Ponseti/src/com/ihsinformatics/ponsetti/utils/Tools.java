package com.ihsinformatics.ponsetti.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ihsinformatics.ponsetti.screens.ImageCapturer;

public final class Tools {

	private static Tools tools;
	
	private Tools() {
		
	}
	
	public static Tools getInstance() {
		if(tools == null) {
			tools = new Tools();
		}
		return tools;
	}
	
	public List<String> findSelectedCheckboxesValues(String data) {
		List<String> list = new ArrayList<String>();
		String temp = "";
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) != ';') {
				temp += data.charAt(i);
			} else {
				list.add(temp);
				temp = "";
			}
		}
		list.add(temp);
		return list;
	}
	
	public String getViewText(View v) {
		if(v instanceof EditText) {
			String text = ((EditText) v).getText().toString();
			if(text!=null && text.length()>0) {
				
				return text;
			}
		} else if(v instanceof RadioGroup) {
			View rv = v.getRootView();
			RadioButton rb = (RadioButton)rv.findViewById(((RadioGroup)v).getCheckedRadioButtonId()); 
			if (rb != null) {
				return rb.getText().toString();
			}
			
		} else if(v instanceof CheckBox) {
			
				return ((CheckBox) v).getText().toString();
			
		}  else if(v instanceof Spinner) {
			Object obj = ((Spinner)v).getSelectedItem();
			if(obj!=null) {
				return obj.toString();
			}
		}
		
		return "null";
	}
	
	/*public String getViewText(CheckBox... cbs) {
		String toReturn = "";
		for(CheckBox cb:cbs) {
			if(cb.isChecked()) {
				toReturn+=cb.getText().toString()+"SEP~SEP";
			}
		}
		
		return toReturn;
	}*/
	
	/*public List<String> getCheckBoxesValues(String s) {
		List<String>  toReturn = new ArrayList<String>();
		String temp="";
		for(int i=0; i<s.length(); i++) {
			char ch = s.charAt(i);
			if(ch!= '~') {
				temp+=ch;
			} else {
				toReturn.add(temp);
				temp = "";
			}
		}
		
		return toReturn;
	}*/
	
	public Boolean validate(View view) {
		if(view instanceof EditText) {
			String val = ((EditText) view).getText().toString();
			if(val!=null && val.length()>0 && !areAllSpaces(val)){
				return true;
			}
		} else if (view instanceof Spinner) {
			String val = (((Spinner)view).getSelectedItem()!=null)?((Spinner)view).getSelectedItem().toString():null;
			if(val!=null && val.length()>0 && !areAllSpaces(val)) {
				return true;
			}
		}
		else if(view instanceof RadioGroup) {
			if(((RadioGroup)view).getCheckedRadioButtonId() != -1) {
				return true;
			}
		}
		
		return false;
	}
	
	public Boolean validate(CheckBox... checkboxes) {
		for(int i = 0; i<checkboxes.length; i++) {
			if(checkboxes[i].isChecked())  {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean areAllSpaces(String string) {
		string = string.replaceAll(" ", "");
		
		if(string.length()>0) {
			return false;
		}
		
		return true;
	}
	
	public boolean isICRID(String string) {
		if(string.matches("\\D{3}+\\d{8}")) {
			return true;
		}
		
		return false;
	}
	
	public static byte[] gzdeflate(final byte[] uncompressed) {
			Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION, true);
			deflater.setInput(uncompressed);
			deflater.finish();
			byte[] compressed = new byte[uncompressed.length];
			int compressedLength = deflater.deflate(compressed);
			deflater.end();
			byte[] output = new byte[compressedLength];
			System.arraycopy(compressed, 0, output, 0, compressedLength);	// trim array
			 
			return output;
		} 
	
	public String deflateAndEncodeData(String data) {
		try {
			
			/*InputStream stream = new DeflaterInputStream(new ByteArrayInputStream(data.getBytes()), new Deflater()));
			String inputStream = getStringFromInputStream(stream);
			return Base64.encodeToString(inputStream.getBytes("UTF-8"), Base64.DEFAULT);*/
			
			/*byte[] input = data.getBytes("UTF-8");
		     byte[] output = new byte[100];
		     Deflater deflator = new Deflater();
		     deflator.setInput(input);
		     deflator.finish();
		     int compressedDataLength = deflator.deflate(output);
		     return new String(output, 0, compressedDataLength, "UTF-8");*/
			
			byte[] compressed = gzdeflate(data.getBytes());
			return Base64.encodeToString(compressed,  Base64.DEFAULT);
			
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	public String decodedAndInflateData(String data) {
		try {
			byte[] decoded = Base64.decode(data, Base64.DEFAULT);
			// InputStream stream = new GZIPInputStream(new ByteArrayInputStream(decoded));
            InputStream stream = new InflaterInputStream(new ByteArrayInputStream(decoded), new Inflater(true));
            String rec = getStringFromInputStream(stream);
            
            return rec;
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	public String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
	
	public void manageViewsVisibility(List<View> views, View... viewsToSetVisible) {
		if( viewsToSetVisible !=null && viewsToSetVisible.length >0){
			List<View> l = Arrays.asList(viewsToSetVisible);
			for(View v:views) {
				if(l.contains(v)) {
					v.setVisibility(View.VISIBLE);
				} else {
					v.setVisibility(View.GONE);
				}
			}
		} else {
			for(View v:views) {
				v.setVisibility(View.GONE);
			}
		}
	}

	public void manageViewsVisibility(List<View> views) {
		for(View v:views) {
			v.setVisibility(View.GONE);
		}
	}
	
	public void setSelectedValueToSpinner(Spinner sp, String[] values, String value) {
		List<String> relations = Arrays.asList(values) ;
		sp.setSelection(relations.indexOf(value));
	}
	
	public float calculateFCS(String arg1, String arg2, String arg3) {
		float arg1f = Float.parseFloat(arg1);
		float arg2f = Float.parseFloat(arg2);
		float arg3f = Float.parseFloat(arg3);
		return arg1f+arg2f+arg3f;
	}
	
	public ArrayList<String> stringToArray(String arrayAsString) {
		String[] strings = arrayAsString.replace("[", "").replace("]", "").split(", ");
		ArrayList<String> result = new ArrayList<String>();
		
		for (int i = 0; i < result.size(); i++) {
			result.add(strings[i]);
		}
		
		return result;
	}
	
	public String getBitmapEncodedContent(Bitmap bm) {
		InputStream stream = null;
		String encoded;
		try {
			// Bitmap bitmap =  BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath()+"/Pictures/JPEG_20140519_163621_-23453743.jpg");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
			byte[] byteArrayImage = baos.toByteArray();
			String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
			encoded = encodedImage;
			// System.out.println(encoded);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return encoded;
	}
	
	public Bitmap getBitmapFromEncodedContent(String encodedContent) {
		
		Bitmap bitmap = null;
		try {
			byte[] unEncodedContent = Base64.decode(encodedContent, Base64.DEFAULT);
			Options options = new Options();
			options.inSampleSize = 2;
			bitmap = BitmapFactory.decodeByteArray(unEncodedContent, 0, unEncodedContent.length, options);
		} finally {
			
		}

		return bitmap;
	}
	
public byte[] getBitmapByteArrayFromEncodedContent(String encodedContent) {
		
		
		
			byte[] unEncodedContent = Base64.decode(encodedContent, Base64.DEFAULT);
			

		return unEncodedContent;
	}
	
	public static final String ICR_ID = "icr_id";
	public static final int REQUEST_TAKE_PHOTO = 5;
	
	public void dispatchTakePictureIntent(Activity parent, String icrId) {
		Intent intent = new Intent(parent, ImageCapturer.class);
		Bundle b = new Bundle();
		b.putString(ICR_ID, icrId);
		intent.putExtras(b);
		parent.startActivityForResult(intent, REQUEST_TAKE_PHOTO);
	}
	
	public int getCurrentEpochTime() {
		
		return (int) (System.currentTimeMillis()/1000);
	}
	
	public Date getDateFromEpochTime(int epochTime) {
		
		return  new Date(epochTime*1000L);
	}
	
	public String generateBitmapHash(byte[] data) {
		System.out.println("Start MD5 Digest");
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(data);
			byte[] hash = md.digest();
			return returnHex(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String returnHex(byte[] inBytes) throws Exception {
        String hexString = null;
        for (int i=0; i < inBytes.length; i++) {
        	//for loop ID:1
            hexString += Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        
        // Belongs to for loop ID:1
        return hexString;
	}
	
	public void freezeAllInViewGroup(ViewGroup layout) {
	    layout.setEnabled(false);
	    for (int i = 0; i < layout.getChildCount(); i++) {
	        View child = layout.getChildAt(i);
	        if (child instanceof ViewGroup) {
	        	freezeAllInViewGroup((ViewGroup) child);
	        } else {
	        	child.setClickable(false);
	        	if(child instanceof EditText) {
	        		((EditText)child).setKeyListener(null);
	        		((EditText)child).setOnClickListener(null);
	        		// ((EditText)child).setInputType(InputType.TYPE_NULL);
	        	}
	        	// child.setFocusable(false); // not using because ViewTreeObserver will not work then
	        }
	    }
	}
	
	public void changeTextViewBackGround(TextView textView, Drawable drawable) {
		int left = textView.getPaddingLeft();
		int right = textView.getPaddingRight();
		int top = textView.getPaddingTop();
		int bottom = textView.getPaddingBottom();
		textView.setBackgroundDrawable(drawable);
		textView.setPadding(left, top, right, bottom);
	}
}
