package com.ecom.core.util;

import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public class MimeTypes {

	 
	  public static final String MIME_IMAGE_BMP         = "image/bmp";
	  public static final String MIME_IMAGE_CGM         = "image/cgm";
	  public static final String MIME_IMAGE_GIF         = "image/gif";
	  public static final String MIME_IMAGE_IEF         = "image/ief";
	  public static final String MIME_IMAGE_JPEG          = "image/jpeg";
	  public static final String MIME_IMAGE_TIFF          = "image/tiff";
	  public static final String MIME_IMAGE_PNG         = "image/png";
	  public static final String MIME_IMAGE_SVG_XML       = "image/svg+xml";
	  public static final String MIME_IMAGE_VND_DJVU        = "image/vnd.djvu";
	  public static final String MIME_IMAGE_WAP_WBMP        = "image/vnd.wap.wbmp";
	  public static final String MIME_IMAGE_X_CMU_RASTER      = "image/x-cmu-raster";
	  public static final String MIME_IMAGE_X_ICON        = "image/x-icon";
	  public static final String MIME_IMAGE_X_PORTABLE_ANYMAP   = "image/x-portable-anymap";
	  public static final String MIME_IMAGE_X_PORTABLE_BITMAP   = "image/x-portable-bitmap";
	  public static final String MIME_IMAGE_X_PORTABLE_GRAYMAP  = "image/x-portable-graymap";
	  public static final String MIME_IMAGE_X_PORTABLE_PIXMAP   = "image/x-portable-pixmap";
	  public static final String MIME_IMAGE_X_RGB         = "image/x-rgb";
	 

	  private static HashMap<String, String> mimeTypeMapping;

	  static {
	    mimeTypeMapping = new HashMap<String, String>(200) {
	      private void put1(String key, String value) {
	        if (put(key, value) != null) {
	          throw new IllegalArgumentException("Duplicated extension: " + key);
	        }
	      }
	      {
	     
	      put1(MIME_IMAGE_X_RGB,"rgb");
	      put1(MIME_IMAGE_X_PORTABLE_PIXMAP,"ppm");
	      put1(MIME_IMAGE_X_PORTABLE_GRAYMAP,"pgm");
	      put1(MIME_IMAGE_X_PORTABLE_BITMAP,"pbm");
	      put1(MIME_IMAGE_X_PORTABLE_ANYMAP,"pnm");
	      put1(MIME_IMAGE_X_ICON,"ico");
	      put1( MIME_IMAGE_X_CMU_RASTER,"ras");
	      put1( MIME_IMAGE_WAP_WBMP,"wbmp");
	     
	      put1( MIME_IMAGE_SVG_XML,"svg");
	      put1( MIME_IMAGE_IEF,"ief");
	      put1( MIME_IMAGE_CGM,"cgm");
	      put1( MIME_IMAGE_BMP,"bmp");
	      
	      put1( MIME_IMAGE_GIF,"gif");
	      put1( MIME_IMAGE_JPEG,"jpg");
	      put1( MIME_IMAGE_TIFF,"tif");
	      put1( MIME_IMAGE_PNG,"png");
	      
	    }};
	  }
	  
	  public static String getMimeType(String ext) {
		    String mimeType = mimeTypeMapping.get(ext);
		    if (mimeType == null) {
		      mimeType = "jpg";
		    }
		    return mimeType;
		  }
}
