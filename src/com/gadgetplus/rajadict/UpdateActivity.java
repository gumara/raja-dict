package com.gadgetplus.rajadict;

import android.os.Bundle;
import android.app.Activity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import com.gadgetplus.rajadict.R;

import android.os.AsyncTask;

import android.os.Environment;
import android.os.StrictMode;
import android.annotation.SuppressLint;

import android.app.Dialog;
import android.app.ProgressDialog;

import android.util.Log;

public class UpdateActivity extends Activity {

	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

	private ProgressDialog mProgressDialog;
	private String URLDownload;
	private String txtURL;

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Permission StrictMode
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		startDownload();

	}

	private void startDownload() {
		// editText1
		txtURL = "https://raw.github.com/anoochit/kreandict/master/teenword.db";
		URLDownload = txtURL.toString();
		new DownloadFileAsync().execute(URLDownload);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("กำลังดาวน์โหลด...");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
		}
	}

	public void moveDB() throws Exception {
		String FICHIER_BLOW = "teenword.db";
		File f = new File(getDataDir().toString(), FICHIER_BLOW);
		String f2 = Environment.getExternalStorageDirectory().getPath()
				+ "/teenword.db";

		BufferedOutputStream bufEcrivain = new BufferedOutputStream(
				(new FileOutputStream(f)));
		BufferedInputStream VideoReader = new BufferedInputStream(
				new FileInputStream(f2));
		byte[] buff = new byte[32 * 1024];
		int len;
		while ((len = VideoReader.read(buff)) > 0) {
			bufEcrivain.write(buff, 0, len);
			Log.d("FILE", "move database to app storage");
		}
		bufEcrivain.flush();
		bufEcrivain.close();
	}

	public String getDataDir() {
		return this.getExternalFilesDir(null).toString();
	}

	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {

				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

				InputStream input = new BufferedInputStream(url.openStream());

				// Get File Name from URL
				// String fileName = URLDownload.substring(
				// URLDownload.lastIndexOf('/')+1, URLDownload.length() );

				OutputStream output = new FileOutputStream(Environment
						.getExternalStorageDirectory().getPath()
						+ "/teenword.db");

				Log.d("DEBUG", Environment.getExternalStorageDirectory()
						.getPath() + "/teenword.db");

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();

				moveDB();
				finish();

			} catch (Exception e) {
				finish();
			}

			return null;

		}

		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC", progress[0]);
			mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String unused) {
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		}
	}

}
