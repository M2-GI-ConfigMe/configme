<template>
  <v-app>
    <ribbon></ribbon>
    <div id="app-header">
      <navbar></navbar>
    </div>
    <div class="container-fluid mt-10">
      <div class="card jh-card">
        <router-view></router-view>
      </div>
      <b-modal id="login-page" hide-footer lazy>
        <span data-cy="loginTitle" slot="modal-title" id="login-title">Se connecter</span>
        <login-form></login-form>
      </b-modal>

      <jhi-footer></jhi-footer>
    </div>
  </v-app>
</template>

<script lang="ts" src="./app.component.ts"></script>
