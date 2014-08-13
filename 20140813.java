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