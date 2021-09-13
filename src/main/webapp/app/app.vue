<template>
  <div id="app">
    <ribbon></ribbon>
    <div id="app-header">
      <jhi-navbar></jhi-navbar>
    </div>
    <div class="container-fluid">
      <div class="card jh-card">
        <router-view></router-view>
      </div>
      <b-modal id="login-page" hide-footer lazy>
        <span slot="modal-title" id="login-title">Se connecter</span>
        <login-form></login-form>
      </b-modal>

      <b-modal id="register-page" hide-footer lazy>
        <span slot="modal-title" id="login-title">S'enregister</span>
        <register-page></register-page>
      </b-modal>

      <jhi-footer></jhi-footer>
    </div>
  </div>
</template>

<script lang="ts" src="./app.component.ts"></script>
