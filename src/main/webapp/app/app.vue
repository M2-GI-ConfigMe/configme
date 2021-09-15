<template>
  <v-app>
    <ribbon></ribbon>
    <div id="app-header">
      <navbar></navbar>
    </div>
    <div style="padding-top: 64px; height: 100%">
      <router-view></router-view>
      <b-modal id="login-page" hide-footer lazy>
        <span slot="modal-title" id="login-title">Se connecter</span>
        <login-form></login-form>
      </b-modal>

      <b-modal id="register-page" hide-footer lazy>
        <span slot="modal-title" id="login-title">S'enregister</span>
        <register-form></register-form>
      </b-modal>

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
