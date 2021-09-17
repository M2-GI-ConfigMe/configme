<template>
  <v-dialog v-model="showDialog" max-width="600">
    <v-card class="p-3">
      <v-overlay :value="loading" absolute>
        <v-progress-circular indeterminate size="64"></v-progress-circular>
      </v-overlay>
      <v-card-title class="font-weight-bold text-h4 mb-4"> Se connecter </v-card-title>
      <v-card-text>
        <div class="row justify-content-center">
          <v-alert type="error" v-if="authenticationError" class="mb-1 mt-2">
            <strong>Impossible de se connecter !</strong>Vérifier l'identifiant et le mot de passe puis réessayer.
          </v-alert>
          <div class="col">
            <v-form @submit.stop.prevent="doLogin()">
              <div class="form-group">
                <v-text-field v-model="login" label="E-mail" placeholder="template@email.com" hide-details="auto" outlined required>
                </v-text-field>
              </div>
              <div class="form-group">
                <v-text-field
                  :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="showPass = !showPass"
                  :type="showPass ? 'text' : 'password'"
                  v-model="password"
                  label="Mot de passe"
                  placeholder="********"
                  hide-details="auto"
                  outlined
                  required
                >
                </v-text-field>
              </div>
              <!--          
              code couleur : 2196F3
              -->
              <div class="text-right">
                <v-btn color="primary" class="col-md-4" data-cy="submit" type="submit" @click.stop.prevent="doLogin()">Ok</v-btn>
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
            <div class="d-flex align-items-center">
              <span class="mr-1">Nouveau sur Config'Me ?</span>
              <!-- <a href="/register " class="text-decoration-none">S'inscrire</a> -->
              <v-btn text color="primary" @click="doRegister()"> S'inscrire </v-btn>
            </div>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script lang="ts" src="./login-form.component.ts"></script>
