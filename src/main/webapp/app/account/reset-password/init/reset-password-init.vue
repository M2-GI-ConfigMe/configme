<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8">
        <h1 v-text="$t('reset.request.title')">Reset your password</h1>

        <div class="alert alert-warning" v-if="!success">
          <p v-text="$t('reset.request.messages.info')">Enter the email address you used to register.</p>
        </div>

        <div class="alert alert-success" v-if="success">
          <p v-text="$t('reset.request.messages.success')">Check your emails for details on how to reset your password.</p>
        </div>

        <v-form v-model="isValid" v-on:submit.prevent="requestReset()">
          <div class="form-group">
            <v-text-field
              v-model="$v.resetAccount.email.$model"
              :rules="rules.emailRules.concat(rules.requiredField)"
              type="email"
              label="E-mail"
              placeholder="template@email.com"
              hide-details="auto"
              email
              outlined
              required
            >
            </v-text-field>
          </div>
          <div class="text-right">
            <!-- !isValid -->
            <v-btn :disabled="true" @click="requestReset()" color="primary">Comming soon...</v-btn>
          </div>
        </v-form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./reset-password-init.component.ts"></script>
