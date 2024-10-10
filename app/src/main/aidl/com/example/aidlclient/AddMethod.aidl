// ADDMethod.aidl
package com.example.aidlclient;

// Declare any non-default types here with import statements

interface AddMethod {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   int toAdd(int a,int b);
   int getResult();
}