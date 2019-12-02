/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// [START dataproc_delete_cluster]
import com.google.api.gax.longrunning.OperationFuture;
import com.google.cloud.dataproc.v1.ClusterControllerClient;
import com.google.cloud.dataproc.v1.ClusterControllerSettings;
import com.google.cloud.dataproc.v1.ClusterOperationMetadata;
import com.google.protobuf.Empty;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DeleteCluster {

  public static void deleteCluster(String projectId, String region, String clusterName)
      throws IOException, InterruptedException {
    String myEndpoint = region + "-dataproc.googleapis.com:443";

    // Configure the settings for the cluster controller client
    ClusterControllerSettings clusterControllerSettings =
        ClusterControllerSettings.newBuilder().setEndpoint(myEndpoint).build();

    // Create a cluster controller client with the configured settings. The client only needs to be
    // created once and can be reused for multiple requests. Using a try-with-resources
    // closes the client, but this can also be done manually with the .close() method.
    try (ClusterControllerClient clusterControllerClient =
        ClusterControllerClient.create(clusterControllerSettings)) {
      // Configure the settings for our cluster

      // Delete the Cloud Dataproc cluster
      OperationFuture<Empty, ClusterOperationMetadata> deleteClusterAsyncRequest =
          clusterControllerClient.deleteClusterAsync(projectId, region, clusterName);
      deleteClusterAsyncRequest.get();

      // Print out a success message
      System.out.println("Cluster deleted successfully: " + clusterName);

    } catch (IOException e) {
      // Likely this would occur due to issues authenticating with GCP. Make sure the environment
      // variable GOOGLE_APPLICATION_CREDENTIALS is configured.
      System.err.println("Error deleting the cluster controller client: \n" + e.getMessage());
    } catch (ExecutionException e) {
      // This will likely be due to a cluster of the given name not existing in the given region.
      System.err.println("Error during cluster deletion request: \n" + e.getMessage());
    }
  }
}
// [END dataproc_delete_cluster]
