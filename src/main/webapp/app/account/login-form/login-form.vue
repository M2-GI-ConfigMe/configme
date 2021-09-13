<template>
  <div class="modal-body">
    <div class="row justify-content-center">
      <v-alert v-if="authenticationError">
        <strong>Impossible de se connecter !</strong> Vérifier l'identifiant et le mot de passe puis réessayer.
      </v-alert>
      <div class="col">
        <v-form role="form" v-on:submit.prevent="doLogin()">
          <v-text-field v-model="login" label="E-mail" placeholder="template@email.com" outlined required> </v-text-field>

          <v-text-field v-model="password" type="password" label="Mot de passe" placeholder="********" outlined required> </v-text-field>

          <!--          
          code couleur : 2196F3
          -->
          <div class="text-right">
            <v-btn class="col-md-4" data-cy="submit" type="submit" color="#2196F3">Ok</v-btn>
          </div>
        </v-form>

        <p></p>
        <!-- <div>
          <b-alert show variant="warning">
            <b-link
              :to="'/account/reset/request'"
              class="alert-link"
              v-text="$t('login.password.forgot')"
              data-cy="forgetYourPasswordSelector"
              >Did you forget your password?</b-link
            >
          </b-alert>
        </div> -->
        <div>
          <span>Nouveau sur Config'Me ?</span>
          <a href="/register " class="text-decoration-none">S'inscrire</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./login-form.component.ts"></script>
