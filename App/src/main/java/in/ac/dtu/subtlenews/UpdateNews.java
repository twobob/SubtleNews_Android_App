package in.ac.dtu.subtlenews;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by omerjerk on 2/12/13.
 */
public class UpdateNews extends AsyncTask <Void, Void, String> {

    @Override
    protected String doInBackground(Void... v){

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet("http://subtlenews.appspot.com/.json"));
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (Exception e) {
            Log.d("[GET REQUEST]", "Network exception", e);
            return null;
        }
    }

    protected void onPostExecute(String r){

        Log.d("[GET RESPONSE]", r);

    }
}
