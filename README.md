# SwipeLayout
 
一个简单的SwipeLayout实现效果，主要用到了ViewDragHelper对拖动进行控制，仿QQ侧滑删除，写的不好的地方，还请大神们嘴下留情，当做是笔记放在这里。

先放两张效果图：

![image](https://github.com/AndyRenJie/SwipeLayout/blob/master/device-2017-12-04-151916.png)

![image](https://github.com/AndyRenJie/SwipeLayout/blob/master/device-2017-12-04-151948.png)
 
### 首先自定义FrameLayout

```
public class SwipeLayout extends FrameLayout
```

### 在构造函数中创建ViewDragHelper，ViewDragHelper一般用在一个自定义ViewGroup的内部，

```
public SwipeLayout(Context context) {
        this(context,null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * ViewDragHelper一般用在一个自定义ViewGroup的内部
         * 1.0f是敏感度参数参数越大越敏感
         * this，表示该类生成的对象,他是ViewDragHelper的拖动处理对象，必须为ViewGroup
         */
        dragHelper = ViewDragHelper.create(this,1.0f,callback);
    }
   ```
### 在回调函数中处理水平拖拽，计算控制可滑动范围

```
ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 水平拖拽后处理
         * 计算控制可滑动范围
         * @param child
         * @param left 是指当前拖动子view应该到达的x坐标
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == frontView) {
                if (left > 0) {
                    return 0;
                } else if (left < -range) {
                    return -range;
                }
            } else if (child == backView) {
                if (left > width) {
                    return width;
                } else if (left < width - range) {
                    return width - range;
                }
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == frontView) {
                backView.offsetLeftAndRight(dx);
            } else if (changedView == backView) {
                frontView.offsetLeftAndRight(dx);
            }
            //事件派发
            dispatchSwipeEvent();
            //兼容低版本
            invalidate();
        }

        //松手后根据侧滑位移确定菜单打开与否
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (xvel == 0 && frontView.getLeft() < -range * 0.5f) {
                open();
            } else if (xvel < 0) {
                open();
            } else {
                close();
            }
        }

        //子View如果是clickable，必须重写的方法
        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }
    };
```
   
