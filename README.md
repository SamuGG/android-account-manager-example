## Android Account Manager Example App
<br/>
Android app using the [Account Manager](http://developer.android.com/reference/android/accounts/AccountManager.html) service to handle custom application user accounts.

Example shows how to create custom app accounts on your device, choose between them and authenticate them by calling getAuthToken.
There are 2 activities:

- LoginActivity
- MainActivity

The **MainActivity** requires to be authenticated, so it will open LoginActivity automatically if no auth token could be retrieved from any acount.

The **LoginActivity** is used for login and registration purposes equally. Once authenticated, the app stores in Preferences the account name and the generated auth token.

### How to login
In this demo, a static account repository was created with dummy accounts, which you may use for testing:

- user@example.com / password: pass
- demo@example.com / password: demo
- foo@example.com / password: foobar

### How to create an account
You can create app accounts either from the LoginActivity opened at the app start, or by navigating to your device general settings -> Accounts -> + Add account.

Multiple accounts can be created.


![Screenshot 1](screenshots/samugg-android-account-manager-example-screenshot-1.png?raw=true) &nbsp; ![Screenshot 2](screenshots/samugg-android-account-manager-example-screenshot-2.png?raw=true) &nbsp; ![Screenshot 3](screenshots/samugg-android-account-manager-example-screenshot-3.png?raw=true)


License
=======

Copyright 2014 Samuel Granados García

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
