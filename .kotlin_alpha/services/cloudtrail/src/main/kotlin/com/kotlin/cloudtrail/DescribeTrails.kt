// snippet-comment:[These are tags for the AWS doc team's sample catalog. Do not remove.]
// snippet-sourcedescription:[DescribeTrails.kt demonstrates how to look up information about a trail.]
//snippet-keyword:[AWS SDK for Kotlin]
// snippet-service:[AWS CloudTrail]
// snippet-keyword:[Code Sample]
// snippet-sourcetype:[full-example]
// snippet-sourcedate:[11/03/2021]
// snippet-sourceauthor:[AWS - scmacdon]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/

package com.kotlin.cloudtrail

//snippet-start:[cloudtrail.kotlin.describe_trail.import]
import aws.sdk.kotlin.services.cloudtrail.CloudTrailClient
import aws.sdk.kotlin.services.cloudtrail.model.DescribeTrailsRequest
import aws.sdk.kotlin.services.cloudtrail.model.CloudTrailException
import kotlin.system.exitProcess
//snippet-end:[cloudtrail.kotlin.describe_trail.import]

suspend fun main(args: Array<String>) {

    val usage = """

    Usage:
        <trailName>  

    Where:
        trailName - the name of the trail. 
      
    """

    if (args.size != 1) {
        println(usage)
        exitProcess(0)
      }

    val trailName = args[0]
    val cloudTrailClient = CloudTrailClient{ region = "us-east-1" }
    describeSpecificTrails(cloudTrailClient, trailName)
    cloudTrailClient.close()
}

  //snippet-start:[cloudtrail.kotlin.describe_trail.main]
  suspend fun describeSpecificTrails(cloudTrailClient: CloudTrailClient, trailName: String) {

        try {

            val trailsRequest = DescribeTrailsRequest {
                trailNameList = listOf(trailName)
            }

            val response = cloudTrailClient.describeTrails(trailsRequest)
            response.trailList?.forEach { trail ->
                println("The ARN of the trail is ${trail.trailArn}")
           }
           

        } catch (ex: CloudTrailException) {
            println(ex.message)
            cloudTrailClient.close()
            exitProcess(0)
        }
    }
//snippet-end:[cloudtrail.kotlin.describe_trail.main]