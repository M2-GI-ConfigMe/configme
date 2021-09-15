<template>
  <v-app>
    <ribbon></ribbon>
    <div id="app-header">
      <navbar></navbar>
    </div>
    <div style="padding-top: 64px; height: 100%; background: #f9f9f9">
      <router-view></router-view>
      <b-modal id="login-page" hide-footer lazy>
        <span data-cy="loginTitle" slot="modal-title" id="login-title">Se connecter</span>
        <login-form></login-form>
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
