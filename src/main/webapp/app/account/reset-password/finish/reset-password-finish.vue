<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8">
        <h1 v-text="$t('reset.request.title')">Reset your password</h1>

        <div class="alert alert-danger" v-html="$t('reset.finish.messages.keymissing')" v-if="keyMissing">
          <strong>The password reset key is missing.</strong>
        </div>

        <div class="alert alert-danger" v-if="error">
          <p v-text="$t('reset.finish.messages.error')">
            Your password couldn't be reset. Remember a password request is only valid for 24 hours.
          </p>
        </div>

        <div class="alert alert-success" v-if="success">
          <span v-html="$t('reset.finish.messages.success')"><strong>Your password had been reset.</strong> Please </span>
          <a class="alert-link" v-on:click="openLogin" v-text="$t('global.messages.info.authenticated.link')">sign in</a>
        </div>
        <div class="alert alert-danger" v-if="doNotMatch">
          <p v-text="$t('global.messages.error.dontmatch')">The password and its confirmation do not match!</p>
        </div>

        <div class="alert alert-warning" v-if="!success && !keyMissing">
          <p v-text="$t('reset.finish.messages.info')">Choose a new password.</p>
        </div>

        <div v-if="!keyMissing">
          <v-form v-model="isValid" v-on:submit.prevent="finishReset()">
            <div class="form-group">
              <v-text-field
                v-model="$v.resetAccount.newPassword.$model"
                :rules="rules.passwordRules.concat(rules.requiredField)"
                :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPass = !showPass"
                :type="showPass ? 'text' : 'password'"
                label="Mot de passe"
                placeholder="********"
                hide-details="auto"
                outlined
                required
              >
              </v-text-field>
            </div>
            <div class="form-group">
              <v-text-field
                v-model="$v.resetAccount.confirmPassword.$model"
                :rules="rules.confirmPasswordRules.concat(rules.requiredField)"
                :append-icon="showPass2 ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPass2 = !showPass2"
                :type="showPass2 ? 'text' : 'password'"
                label="Confirmation de mot de passe"
                placeholder="********"
                hide-details="auto"
                outlined
                required
              >
              </v-text-field>
            </div>
            <div class="text-right">
              <v-btn :disabled="!isValid" @click="finishReset()" color="primary">Sauvegarder</v-btn>
            </div>
          </v-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./reset-password-finish.component.ts"></script>
