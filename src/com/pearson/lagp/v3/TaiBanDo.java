package com.pearson.lagp.v3;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.TextureOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class TaiBanDo {
 public static TMXTiledMap getTMXTiledMap(Scene mScene, Engine mEngine,  Context mContext, String maps_name, final Activity activity){
  TMXTiledMap mTMXTiledMap;  
  try{
            final TMXLoader tmxLoader = new TMXLoader(mContext, mEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, new ITMXTilePropertiesListener(){
    public void onTMXTileWithPropertiesCreated(TMXTiledMap pTMXTiledMap, TMXLayer pTMXLayer, TMXTile pTMXTile,
      TMXProperties pTMXTileProperties){
    }                    
            });
            mTMXTiledMap = tmxLoader.loadFromAsset(mContext, "tmx/"+maps_name);
            return mTMXTiledMap;
     }catch (final TMXLoadException tmxle) {
      // Nếu không tải được sẽ bắn ra Dialog cảnh báo. Chọn OK đ�?dừng ứng dụng.
      String error = tmxle.toString();
   AlertDialog.Builder builder = new AlertDialog.Builder(activity);
   builder.setTitle("Error 20:");
   builder.setMessage("Load map false ! - " + error);
   builder.setCancelable(false);
   builder.setNegativeButton("OK", new DialogInterface.OnClickListener(){
    public void onClick(DialogInterface dialog, int which){
     activity.finish();
    }
   });
   AlertDialog dialog = builder.create();
   dialog.show();
     }
  return null;
 } 
}