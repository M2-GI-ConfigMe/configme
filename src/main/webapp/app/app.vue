<template>
  <v-app>
    <ribbon></ribbon>
    <div id="app-header">
      <navbar></navbar>
    </div>
    <div style="padding-top: 64px; height: 100%; background: #f9f9f9">
      <router-view style="min-height: 100%"></router-view>

      <login-form :show="showLogin" @close="showLogin = false"></login-form>
      <register-form :show="showRegister" @close="showRegister = false"></register-form>

      <jhi-footer></jhi-footer>
    </div>
  </v-app>
</template>

<script lang="ts" src="./app.component.ts"></script>

<style>
body {
  height: 100%;
}
html {
  height: 100%;
}
</style>
