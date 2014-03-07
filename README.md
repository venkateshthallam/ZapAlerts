#ZapAlerts

This application allows users to save products available on Zappos.com and send notifications when the product price drops by 20%.

Workflow of the application:
Search UI -> displays products with check boxes to select and save them -> Products saved into the database.

**Price notifier using a cron job**

A java program is run for every one hour using a cron job to hit zappos.com for all the saved products and see if the prices has dropped. If the prices have dropped,then it pushes a email notification.

The user authentication is not done yet,used some hard coded values to send notifications. (will complete that part using OAuth).

To run this, you can import the 'ZapAlerts' folder into eclipse and run it as a web application.
