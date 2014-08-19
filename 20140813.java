//�ڳ������еİ�ť�ڵ��ʱ����� app�Ƿ�װ�����в�ͬ�Ĳ������������ʵ��������������

//1ÿ�ε����ť�� ʹ�ü��汾���Ƿ����0�ķ�ʽ�ж� �Ƿ�װ
 try {
	PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
	isInstall = packageInfo.versionCode > 0;
} catch (Exception e) {
		
}

//2 ʹ�ù㲥�����ߣ�������װ��ж�ض���,�ݴ��޸��Ƿ�װ�ı�־λ��ÿ�ε��ʱ���ݱ�־λ�ж� �Ƿ�װ

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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//ViewPager��Ƕ��ViewPager��Gallery�ȿ������һ�������viewʱ����ν������¼����ݵ���view	
�����˽�һ��onInterceptTouchEvent��onTouchEvent
onTouchEvent����view�ж����һ�������������ݵ�view �������¼���
onInterceptTouchEvent()��ViewGroup��һ��������Ŀ������ϵͳ���ViewGroup�������childView����onTouchEvent()֮ǰ������¼�����һ�����أ�Android��ô��Ƶ��뷨Ҳ�ܺ���⣬����ViewGroup���������childView,�����Ҫ�ܹ�ͳһ��ظ���touch�¼��Ļ��ᣬ��˴���Ĳ��ܰ�����view�Ŀؼ���û����������ģ���LinearLayout���У�TextView��û�С� 
onInterceptTouchEvent()ʹ��Ҳ�ܼ򵥣������ViewGroup�︲д�˸÷�������ô�Ϳ��ԶԸ���touch�¼��������ء�����������أ��Ƿ����е�touch�¼�����Ҫ�������ǱȽϸ��ӵģ�touch�¼���onInterceptTouchEvent()��onTouchEvent�Լ�����childView��Ĵ��ݻ�����ȫȡ����onInterceptTouchEvent()��onTouchEvent()�ķ���ֵ�����ң����down�¼�����ķ���ֱֵ��Ӱ�쵽����move��up�¼��Ľ��պʹ��ݡ� 
���ڷ���ֵ�����⣬�����������������return true,��ô��ʾ�÷��������˴˴��¼������return false����ô��ʾ�÷�����δ������ȫ�����¼���Ȼ��Ҫ��ĳ�ַ�ʽ������ȥ�����ȴ�����

Ҫ������������ַ�����
 1 ��д��ViewPager��onInterceptTouchEvent()��������ⴥ�����Ƿ����ӿؼ��Ŀɻ����ؼ��ϣ������¼��Ƿ����ء�

2 ���ӿؼ���ע�븸ViewPagerʵ�������ÿؼ���getParent()��������Ȼ�����ӿؼ���onTouchEvent��onInterceptTouchEvent��dispatchTouchEvent������߸�ViewPager��Ҫ���ظÿؼ��ϵĴ����¼������磺
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