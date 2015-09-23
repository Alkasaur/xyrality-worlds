package com.xirality.worlds.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

public abstract class BaseTask<T> extends AsyncTask<Object, Void, TaskResult<T>> {

	protected Context context;
	protected ProgressDialog progressDialog;
	private final CharSequence progressDialogMessage;
	
	private boolean allowCancel = true;
	
	private TaskSuccessListener<T> successListener;
	private TaskFailureListener failureListener;
	private TaskCancelListener cancelListener;
	
	protected abstract TaskResult<T> process(Object... params) throws Exception;
	
    public interface TaskSuccessListener<T> {
    	public void onTaskSuccess(T result);
    }
   
    public interface TaskFailureListener {
    	public void onTaskFailure(Throwable e);
    }
    
    public interface TaskCancelListener {
    	public void onTaskCancelled();
    }
    /**
     * Set @param progressDialogMessage to null if you don't want progress to pop up
     * */
	public BaseTask(Context context, String progressDialogMessage, boolean allowCancel) {
		this.context = context;
		this.progressDialogMessage = progressDialogMessage;
		this.allowCancel = allowCancel;
	}
	
	/**
     * Set @param progressDialogMessage to null if you don't want progress to pop up
     * */
	public BaseTask(Context context, 
			String progressDialogMessage, 
			boolean allowCancel,
			TaskSuccessListener<T> successListener,
			TaskFailureListener failureListener,
			TaskCancelListener cancelListener) {
        this(context, progressDialogMessage, allowCancel);
        	
        this.successListener = successListener;
        this.failureListener = failureListener;
        this.cancelListener = cancelListener;
    }
	
	/*
	 * Invoked on the UI thread immediately after the task is executed. 
	 * This step is normally used to setup the task, for instance by showing a progress bar in the user interface.
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (isShowPopupDialog()) {
			progressDialog = ProgressDialog.show(
					context, 
					null /* no title */,
					progressDialogMessage /* message */,
					true /* indeterminate */,
					allowCancel /* cancellable? */,
					new ProgressDialog.OnCancelListener() {
						public void onCancel(DialogInterface dialog) {
							cancel(true);
						}
					});
		}
	}
	
	protected TaskResult<T> doInBackground(Object... params) {
		try {
			return process(params);
		}
		catch (Exception e) {
			return new TaskResult<T>(e);
		}
	}
	
	/*
	 * invoked on the UI thread after the background computation finishes. 
	 * The result of the background computation is passed to this step as a parameter
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
    public final void onPostExecute(TaskResult<T> result) {
		super.onPostExecute(result);
		try {
			hideProgress();
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "Probably activity was destroyed by framework: ", e);
			return;
		}
		
		if(!result.hadException()) {
			if (successListener != null) {
			    successListener.onTaskSuccess(result.getResult());
			}
		}
		else {
			if (failureListener != null) {
			    failureListener.onTaskFailure(result.getException());
			}
		}
		
    }

	@Override
	protected void onCancelled() {
		super.onCancelled();
		hideProgress();
		if (cancelListener != null) {
		    cancelListener.onTaskCancelled();
		}
	}

	public boolean isAllowCancel() {
		return allowCancel;
	}

	public void setAllowCancel(boolean allowCancel) {
		this.allowCancel = allowCancel;
	}
	
	public boolean isShowPopupDialog() {
		return !TextUtils.isEmpty(progressDialogMessage);
	}

	private void hideProgress() {
		Log.d(this.getClass().getName(), "Hiding the progress dialog");
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
    	Log.d(this.getClass().getName(), "Hidden");
	}
}
