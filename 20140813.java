//在程序中有的按钮在点击时会根据 app是否安装来进行不同的操作，对于这个实现有两个方法：

//1每次点击按钮后 使用检查版本号是否大于0的方式判断 是否安装
 try {
	PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
	isInstall = packageInfo.versionCode > 0;
} catch (Exception e) {
		
}

//2 使用广播接收者，监听安装和卸载动作,据此修改是否安装的标志位，每次点击时根据标志位判断 是否安装

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (inflater == null) {
			inflater = LayoutInflater.from(mContext);
		}
		registerIntentReceivers();  
		return inflater.inflate(R.layout.ty_comment_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		initViews(view);
		initDatas();
		initActions();
		startRequest();
	}
	
	@Override
	public void onDestroy() {
		unregisterIntentReceivers();
		super.onDestroy();
	}
	
	private void registerIntentReceivers() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        activity.registerReceiver(mApplicationsReceiver, intentFilter);
    }
	
	private void unregisterIntentReceivers() {
    	if (mApplicationsReceiver != null){
    		activity.unregisterReceiver(mApplicationsReceiver);
    	}    	
    }
	
	private final BroadcastReceiver mApplicationsReceiver = new BroadcastReceiver() {
        @Override
		public void onReceive(Context context, Intent intent) {
        	String action = intent.getAction();
        	if(action.equals(Intent.ACTION_PACKAGE_ADDED) || action.equals(Intent.ACTION_PACKAGE_REMOVED)){
        		String schemePart = intent.getData().getEncodedSchemeSpecificPart();
        		if(packageName != null && !packageName.equals("")) {
        			if (StringUtil.equals(schemePart, packageName)) {
    					if (action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
    						isInstall = false;
    					} else {
    						isInstall = true;
    						mSendComment.setEnabled(true);
    						mTYInputContent.setFocusable(true);
    						mTYInputContent.setFocusableInTouchMode(true);   
    						//Log.i(TAG, "mApplicationsReceiver mTYInputContent"+mTYInputContent.isFocusable()+" "+mTYInputContent.isEnabled());
    					}
    				}
        		}
        	}
        }
    };
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//ViewPager中嵌套ViewPager、Gallery等可以左右滑动的子view时，如何将滑动事件传递到子view	
首先了解一下onInterceptTouchEvent与onTouchEvent
onTouchEvent是在view中定义的一个方法。处理传递到view 的手势事件。
onInterceptTouchEvent()是ViewGroup的一个方法，目的是在系统向该ViewGroup及其各个childView触发onTouchEvent()之前对相关事件进行一次拦截，Android这么设计的想法也很好理解，由于ViewGroup会包含若干childView,因此需要能够统一监控各种touch事件的机会，因此纯粹的不能包含子view的控件是没有这个方法的，如LinearLayout就有，TextView就没有。 
onInterceptTouchEvent()使用也很简单，如果在ViewGroup里覆写了该方法，那么就可以对各种touch事件加以拦截。但是如何拦截，是否所有的touch事件都需要拦截则是比较复杂的，touch事件在onInterceptTouchEvent()和onTouchEvent以及各个childView间的传递机制完全取决于onInterceptTouchEvent()和onTouchEvent()的返回值。并且，针对down事件处理的返回值直接影响到后续move和up事件的接收和传递。 
关于返回值的问题，基本规则很清楚，如果return true,那么表示该方法消费了此次事件，如果return false，那么表示该方法并未处理完全，该事件仍然需要以某种方式传递下去继续等待处理。

要解决问题有两种方法：
 1 重写父ViewPager的onInterceptTouchEvent()方法，检测触摸点是否在子控件的可滑动控件上，决定事件是否拦截。

2 在子控件中注入父ViewPager实例（调用控件的getParent()方法），然后在子控件的onTouchEvent，onInterceptTouchEvent，dispatchTouchEvent里面告诉父ViewPager不要拦截该控件上的触摸事件。例如：
public boolean onTouch(View v, MotionEvent event) {

    switch(event.getAction()) {

    case MotionEvent.ACTION_MOVE:
        pager.requestDisallowInterceptTouchEvent(true);
        break;
		
    case MotionEvent.ACTION_UP:

    case MotionEvent.ACTION_CANCEL:
        pager.requestDisallowInterceptTouchEvent(false);
        break;
    }
}