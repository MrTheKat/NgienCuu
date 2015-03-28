package com.pearson.lagp.v3;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.opengl.texture.source.*;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.modifier.ease.EaseLinear;
import org.anddev.andengine.util.modifier.ease.EaseSineInOut;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.modifier.PathModifier.Path;

import com.qwerjk.andengine.entity.shape.IPixelPerfectShape;
import com.qwerjk.andengine.entity.sprite.PixelPerfectAnimatedSprite;
import com.qwerjk.andengine.entity.sprite.PixelPerfectSprite;
import com.qwerjk.andengine.opengl.texture.region.PixelPerfectTextureRegion;
import com.qwerjk.andengine.opengl.texture.region.PixelPerfectTextureRegionFactory;
import com.qwerjk.andengine.opengl.texture.region.PixelPerfectTiledTextureRegion;
 


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends BaseGameActivity{

 
 private static final int CAMERA_WIDTH = 480;
 private static final int CAMERA_HEIGHT = 800;


 
 private BitmapTextureAtlas texture;
 private PixelPerfectTiledTextureRegion textureRegion;
 private int statusCar =0;// 0 là không chuyển đông; 1 là chuyển động
 

 private BitmapTextureAtlas textureVC;
 private PixelPerfectTextureRegion textureRegionVC;

 private BitmapTextureAtlas mOnScreenControlTexture;
 private BitmapTextureAtlas mOnScreenControlTexture1;
 private BitmapTextureAtlas mOnScreenControlTexture2;
 private TextureRegion mOnScreenControlBaseTextureRegion;
 private TextureRegion mOnScreenControlKnobTextureRegion;
 private PixelPerfectTextureRegion mOnScreenControlKnobTextureRegion2;
 
 private BoundCamera mCamera;
 private Texture mTexture;
 //Tạo biến quản lí ảnh animation
 private TiledTextureRegion mXeTangTiledTextureRegion;

 private Texture nutBanTexture;
 private TextureRegion nutBanTextureRegion;
 
 private TMXTiledMap mTmxTiledMap;
 private TMXLayer VatCanTMXLayer;
 private String tenBanDo = "map1.tmx";
 
 float pX = 0, pY = 0;
 float pX2 = 0, pY2 =0;
 
 @Override
 public Engine onLoadEngine() {
	 this.mCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
     return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT,
    		 new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
 
 }

 @Override
 public void onLoadResources() {
     	PixelPerfectTextureRegionFactory.setAssetBasePath("gfx/");
	   BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	   
	   this.texture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    this.textureRegion = PixelPerfectTextureRegionFactory.createTiledFromAsset(this.texture, this, "nv2.png",0, 0,4, 4);
	    this.mEngine.getTextureManager().loadTexture(this.texture);
	    
	  this.textureVC = new BitmapTextureAtlas(2048, 2048);
	  this.textureRegionVC = PixelPerfectTextureRegionFactory.createFromAsset(this.textureVC, this, "vatcan.png", 0, 0);
	    
	    
	    
	  this.mOnScreenControlTexture = new BitmapTextureAtlas(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	  this.mOnScreenControlTexture1 = new BitmapTextureAtlas(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	  this.mOnScreenControlTexture2 = new BitmapTextureAtlas(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	  this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset( (BitmapTextureAtlas) 
			  this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
	  this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset( (BitmapTextureAtlas)
	          this.mOnScreenControlTexture1, this, "onscreen_control_knob.png", 128, 0);
	  
	  this.mOnScreenControlKnobTextureRegion2 = PixelPerfectTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture2, this,"onscreen_control_knob.png" , 128,0);
	
	  this.mEngine.getTextureManager().loadTextures(this.texture, this.mOnScreenControlTexture, this.mOnScreenControlTexture1, this.mOnScreenControlTexture2,this.textureVC);

 }

 @Override
 public Scene onLoadScene() {
	  this.mEngine.registerUpdateHandler(new FPSLogger());
	
	  final Scene scene = new Scene();
	  scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
	  
	//khoi tao map
	  mTmxTiledMap = TaiBanDo.getTMXTiledMap(scene, mEngine, this, tenBanDo, this);
	
	  ArrayList<TMXLayer> mapLayers = mTmxTiledMap.getTMXLayers();
	  for(TMXLayer layer : mapLayers){
	      if(layer.getName().equals("vatcan")){
	          VatCanTMXLayer = layer;
	      }
	      scene.attachChild(layer);
	  }
	  
	  final PixelPerfectSprite Vatcan1 = new PixelPerfectSprite(0, 0, this.textureRegionVC);
	  
	  // load ảnh lên màn hình game
	  final PixelPerfectAnimatedSprite car = new PixelPerfectAnimatedSprite(0, 0, this.textureRegion);
	  final PhysicsHandler physicsHandler = new PhysicsHandler(car);
	  //car.setScale(2);
	  car.registerUpdateHandler(physicsHandler);
	  
	  final PixelPerfectSprite VCan = new PixelPerfectSprite(64, 128, this.mOnScreenControlKnobTextureRegion2);
	  scene.attachChild(VCan);
	  scene.attachChild(Vatcan1);
	  scene.attachChild(car);
	  
	  final TMXLayer tmxLayer = this.mTmxTiledMap.getTMXLayers().get(0);
      mCamera.setBounds(0, tmxLayer.getWidth(), 0, tmxLayer.getHeight());
      mCamera.setBoundsEnabled(true);
      mCamera.setChaseEntity(car);

      Path ourPath = new Path(3).to(64, 128).to(64, 256).to(62, 128); 
      LoopEntityModifier ourLoop = new LoopEntityModifier(new PathModifier(5.0f, ourPath, EaseSineInOut.getInstance()));  
      VCan.registerEntityModifier(ourLoop);  
     // VCan.registerEntityModifier(new PathModifier(3.0f, ourPath, EaseLinear.getInstance()));
    final DigitalOnScreenControl digitalOnScreenControl = new DigitalOnScreenControl
      (0, CAMERA_HEIGHT - this.mOnScreenControlBaseTextureRegion.getHeight(), 
        this.mCamera, this.mOnScreenControlBaseTextureRegion, 
        this.mOnScreenControlKnobTextureRegion, 0.1f, new IOnScreenControlListener() {
              @Override
              public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
              
              // Log.v("pValueX= "+pValueX, "pValueY= "+pValueY);
               
              if(pValueX != 0 || pValueY != 0){
            	 

                  pX = car.getX() + 16;
                  pY = car.getY() + 16;
                     if(pValueX > 0){
                      //vật di chuyển sang phải
                    	 if(statusCar!=1) statusCar=0;
                         if(statusCar == 0)
                         {
                             car.animate(new long[]{200, 200, 200, 200}, 8, 11, true);
                             statusCar = 1;
                         }
                         else
                         {
                        	 
                         }
                         pX = car.getX() + car.getWidth()+1;
                         pX2 = pX;
                         pY = car.getY()+5;
                         pY2 = car.getY() + car.getHeight()-5;
                     }
                     else if(pValueX < 0){
                        // vật di chuyển sang bên trái
                    	 if(statusCar!=2) statusCar=0;
                        if(statusCar == 0){
                        car.animate(new long[]{200, 200, 200, 200}, 4, 7, true);
                              statusCar = 2;
                        }
                        else 
                        {
                        	
                        }
                        pX = car.getX()-1;
                        pX2 = pX;
                        pY = car.getY()+5;
                        pY2 = car.getY() + car.getHeight()-5;
                     }else if(pValueY > 0){
                         // vật di chuyển xuống dưới
                    	 if(statusCar!=3) statusCar=0;
                         if(statusCar == 0){                          
                            car.animate(new long[]{200, 200, 200, 200}, 0, 3, true);
                            statusCar = 3;
                         }
                         else 
                         {
                        	 
                         }
                         pX = car.getX()+5;
                         pX2 = car.getX() + car.getWidth()-5;
                         pY = car.getY() + car.getHeight()+1;
                         pY2 = pY;
                     }else if(pValueY < 0){
                         // di chuyển lên trên màn hình
                    	 if(statusCar!=4) statusCar=0;
                         if(statusCar == 0){
                            car.animate(new long[]{200, 200, 200, 200}, 12, 15, true);
                            statusCar = 4;
                         }
                         else
                         {
                          
                         }
                         pX = car.getX()+5;
                         pX2 = car.getX() + car.getWidth()-5;
                         pY = car.getY()-1;
                         pY2 = pY;
                      }
	                  
                     TMXTile mTMXTile = VatCanTMXLayer.getTMXTileAt(pX, pY);
                     TMXTile mTMXTile2 = VatCanTMXLayer.getTMXTileAt(pX2, pY2);
                     try{
                         if(mTMXTile != null){
                             TMXProperties<TMXTileProperty> mTMXProperties = mTMXTile.getTMXTileProperties(mTmxTiledMap);
                             TMXProperty mTmxProperty = mTMXProperties.get(0);
                             if(mTmxProperty.getName().equals("vatcan")){
                                 Log.v("đã dừng", "Chạm phải vật cản rồi !!!");
                                 //Khi chạm vật cản, thiết lập tốc độ= 0
                                 physicsHandler.setVelocity(pValueX * 0, pValueY * 0);
                             }
                         }
                     }catch(Exception e){
                    	 try{
                             if(mTMXTile2 != null){
                                 TMXProperties<TMXTileProperty> mTMXProperties2 = mTMXTile2.getTMXTileProperties(mTmxTiledMap);
                                 TMXProperty mTmxProperty2 = mTMXProperties2.get(0);
                                 if(mTmxProperty2.getName().equals("vatcan")){
                                     Log.v("đã dừng", "Chạm phải vật cản rồi !!!");
                                     //Khi chạm vật cản, thiết lập tốc độ= 0
                                     physicsHandler.setVelocity(pValueX * 0, pValueY * 0);
                                 }
                             }
                         }catch(Exception e2){
                             Log.v("đang di chuyển ", "Không có vật cản!");
                             //Khi không gặp chạm vật cản cho xe tăng di chuyển
                             physicsHandler.setVelocity(pValueX * 100, pValueY *100);
                         }   
                     }   
                     //physicsHandler.setVelocity(pValueX * 100, pValueY * 100); 
                     
              }else{// khi mà nhả bấm coltroll
                  if(statusCar ==1)
                  {
                   car.animate(new long[]{200}, new int[]{6}, 10000);
                  }
                  if(statusCar ==2)
                  {
                   car.animate(new long[]{200}, new int[]{4}, 10000);
                  }
                  if(statusCar ==3)
                  {
                   car.animate(new long[]{200}, new int[]{0}, 10000);
                  }
                  if(statusCar ==4)
                  {
                   car.animate(new long[]{200}, new int[]{12}, 10000);
                  }
                 
                 statusCar = 0;
                 physicsHandler.setVelocity(pValueX * 0, pValueY * 0);
                 }              
              }
          });
 
		    digitalOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		    digitalOnScreenControl.getControlBase().setAlpha(0.5f);
		    digitalOnScreenControl.getControlBase().setScaleCenter(0, 128);
		    digitalOnScreenControl.getControlBase().setScale(0.75f);
		    digitalOnScreenControl.getControlKnob().setScale(0.75f);
		    digitalOnScreenControl.refreshControlKnobPosition();
		
		    digitalOnScreenControl.setTouchAreaBindingEnabled(true);
		    scene.setChildScene(digitalOnScreenControl);
		    
		    scene.registerUpdateHandler(new IUpdateHandler() {
				@Override
				public void reset() { }

				@Override
				public void onUpdate(final float pSecondsElapsed) {
					if(car.collidesWith(VCan)){
						car.setVisible(false);
					}
					else car.setVisible(true);
				}
		    });
		    
  return scene;
 }

 @Override
 public void onLoadComplete() {
  // TODO Auto-generated method stub
  
 }
 
 public boolean isCollides(AnimatedSprite animSprite1 ,Sprite animSprite2) throws Exception{


	 float diffX = Math.abs( animSprite1.getX() - animSprite2.getX());
	 float diffY = Math.abs( animSprite1.getY() - animSprite2.getY());

	 if(diffX < (animSprite1.getWidth()/2 + animSprite2.getWidth()/3) 
	            && diffY < (animSprite1.getHeight()/2 + animSprite2.getHeight()/3)){

	    return true;
	 }else
	   return false;
	 }
}
 