# Docs for the Azure Web Apps Deploy action: https://github.com/Azure/webapps-deploy
# More GitHub Actions for Azure: https://github.com/Azure/actions

name: Java CI/CD

on:
  push:
    branches:
      - master

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up Java
      uses: actions/setup-java@v3
      with:
        distribution: 'temurin'
        java-version: '21'

    - name: Build with Maven
      run: mvn clean install

    - name: Upload build artifacts
      uses: actions/upload-artifact@v3
      with:
        name: java-app
        path: target/*.jar

  deploy:
    runs-on: windows-latest
    needs: build

    steps:
    - name: Download build artifacts
      uses: actions/download-artifact@v3
      with:
        name: java-app

    - name: Login to Azure
      uses: azure/login@v1
      with:
        client-id: ${{ secrets.AZUREAPPSERVICE_CLIENTID }}
        tenant-id: ${{ secrets.AZUREAPPSERVICE_TENANTID }}
        subscription-id: ${{ secrets.AZUREAPPSERVICE_SUBSCRIPTIONID }}

    - name: Deploy to Azure Web App
      uses: azure/webapps-deploy@v2
      with:
        app-name: 'backendcar'
        slot-name: 'Production'
        package: '*.jar'

