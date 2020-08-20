
/**
 * AdministratorServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:05:37 GMT)
 */

    package com.marko.service;

    /**
     *  AdministratorServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AdministratorServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AdministratorServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AdministratorServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for createAccount method
            * override this method for handling normal response from createAccount operation
            */
           public void receiveResultcreateAccount(
                    com.marko.service.AdministratorServiceStub.CreateAccountResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createAccount operation
           */
            public void receiveErrorcreateAccount(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for blockUser method
            * override this method for handling normal response from blockUser operation
            */
           public void receiveResultblockUser(
                    com.marko.service.AdministratorServiceStub.BlockUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from blockUser operation
           */
            public void receiveErrorblockUser(java.lang.Exception e) {
            }
                


    }
    