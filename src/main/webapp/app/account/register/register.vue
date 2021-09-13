<template>
  <div class="modal-body">
    <!-- <div class="row justify-content-center">
      <div class="col-md-8 toastify-container">

        <div class="alert alert-success" role="alert" v-if="success" v-html="$t('register.messages.success')">
          <strong>Registration saved!</strong> Please check your email for confirmation.
        </div>

        <div class="alert alert-danger" role="alert" v-if="error" v-html="$t('register.messages.error.fail')">
          <strong>Registration failed!</strong> Please try again later.
        </div>

        <div class="alert alert-danger" role="alert" v-if="errorUserExists" v-html="$t('register.messages.error.userexists')">
          <strong>Login name already registered!</strong> Please choose another one.
        </div>

        <div class="alert alert-danger" role="alert" v-if="errorEmailExists" v-html="$t('register.messages.error.emailexists')">
          <strong>Email is already in use!</strong> Please choose another one.
        </div>
      </div>
    </div> -->
    <div class="row justify-content-center">
      <div class="col">
        <form id="register-form" name="registerForm" role="form" v-on:submit.prevent="register()" v-if="!success" no-validate>
          <p class="font-weight-bold">Informations de connexion</p>
          <div class="form-group">
            <v-text-field
              v-model="registerAccount.login"
              label="E-mail"
              placeholder="template@email.com"
              hide-details="auto"
              outlined
              required
            >
            </v-text-field>
            <!-- <label class="form-control-label" for="email"> E-mail</label>
            <input
              type="email"
              class="form-control"
              id="email"
              name="email"
              :class="{ valid: !$v.registerAccount.email.$invalid, invalid: $v.registerAccount.email.$invalid }"
              v-model="$v.registerAccount.email.$model"
              minlength="5"
              maxlength="254"
              email
              required
              v-bind:placeholder="$t('global.form[\'email.placeholder\']')"
              data-cy="email"
            /> -->
            <!-- <div v-if="$v.registerAccount.email.$anyDirty && $v.registerAccount.email.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.registerAccount.email.required"
                v-text="$t('global.messages.validate.email.required')"
              >
                Your email is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.registerAccount.email.email"
                v-text="$t('global.messages.validate.email.invalid')"
              >
                Your email is invalid.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.registerAccount.email.minLength"
                v-text="$t('global.messages.validate.email.minlength')"
              >
                Your email is required to be at least 5 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.registerAccount.email.maxLength"
                v-text="$t('global.messages.validate.email.maxlength')"
              >
                Your email cannot be longer than 100 characters.
              </small>
            </div> -->
          </div>
          <div class="form-group">
            <v-text-field
              v-model="registerAccount.password"
              type="password"
              label="Mot de passe"
              placeholder="********"
              hide-details="auto"
              outlined
              required
            >
            </v-text-field>

            <!-- <label class="form-control-label" for="firstPassword" v-text="$t('global.form[\'newpassword.label\']')">New password</label>
            <input
              type="password"
              class="form-control"
              id="firstPassword"
              name="password"
              :class="{ valid: !$v.registerAccount.password.$invalid, invalid: $v.registerAccount.password.$invalid }"
              v-model="$v.registerAccount.password.$model"
              minlength="4"
              maxlength="50"
              required
              v-bind:placeholder="$t('global.form[\'newpassword.placeholder\']')"
              data-cy="firstPassword"
            /> -->
            <!-- <div v-if="$v.registerAccount.password.$anyDirty && $v.registerAccount.password.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.registerAccount.password.required"
                v-text="$t('global.messages.validate.newpassword.required')"
              >
                Your password is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.registerAccount.password.minLength"
                v-text="$t('global.messages.validate.newpassword.minlength')"
              >
                Your password is required to be at least 4 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.registerAccount.password.maxLength"
                v-text="$t('global.messages.validate.newpassword.maxlength')"
              >
                Your password cannot be longer than 50 characters.
              </small>
            </div> -->
          </div>
          <div class="form-group">
            <v-text-field
              v-model="confirmPassword"
              type="password"
              label="Confirmation de mot de passe"
              placeholder="********"
              hide-details="auto"
              outlined
              required
            >
            </v-text-field>

            <!-- <label class="form-control-label" for="secondPassword" v-text="$t('global.form[\'confirmpassword.label\']')"
              >New password confirmation</label
            >
            <input
              type="password"
              class="form-control"
              id="secondPassword"
              name="confirmPasswordInput"
              :class="{ valid: !$v.confirmPassword.$invalid, invalid: $v.confirmPassword.$invalid }"
              v-model="$v.confirmPassword.$model"
              minlength="4"
              maxlength="50"
              required
              v-bind:placeholder="$t('global.form[\'confirmpassword.placeholder\']')"
              data-cy="secondPassword"
            /> -->
            <!-- <div v-if="$v.confirmPassword.$dirty && $v.confirmPassword.$invalid">
              <small
                class="form-text text-danger"
                v-if="!$v.confirmPassword.required"
                v-text="$t('global.messages.validate.confirmpassword.required')"
              >
                Your confirmation password is required.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.confirmPassword.minLength"
                v-text="$t('global.messages.validate.confirmpassword.minlength')"
              >
                Your confirmation password is required to be at least 4 characters.
              </small>
              <small
                class="form-text text-danger"
                v-if="!$v.confirmPassword.maxLength"
                v-text="$t('global.messages.validate.confirmpassword.maxlength')"
              >
                Your confirmation password cannot be longer than 50 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.confirmPassword.sameAsPassword" v-text="$t('global.messages.error.dontmatch')">
                The password and its confirmation do not match!
              </small>
            </div> -->
          </div>

          <p class="font-weight-bold">Informations personnelles</p>
          <div class="form-group">
            <v-row v-bind:no-gutters="true">
              <v-text-field v-model="registerAccount.lastName" label="Nom" placeholder="Dupont" hide-details="auto" outlined required>
              </v-text-field>
              <v-spacer class="mx-2"></v-spacer>
              <v-text-field v-model="registerAccount.firstName" label="Prénom" placeholder="François" hide-details="auto" outlined required>
              </v-text-field>
            </v-row>
          </div>
          <div class="form-group">
            <v-menu ref="menu" v-model="menu" :close-on-content-click="false" transition="scale-transition" offset-y min-width="auto">
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="date"
                  label="Date de naissance"
                  readonly
                  hide-details="auto"
                  outlined
                  required
                  v-bind="attrs"
                  v-on="on"
                >
                </v-text-field>
              </template>
              <v-date-picker
                v-model="date"
                :active-picker.sync="activePicker"
                :max="new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString().substr(0, 10)"
                min="1950-01-01"
                @change="save"
              >
              </v-date-picker>
            </v-menu>
          </div>

          <div class="form-group">
            <v-row v-bind:no-gutters="true">
              <v-text-field v-model="registerAccount.streetNumber" label="N° de Rue" placeholder="5" hide-details="auto" outlined required>
              </v-text-field>
              <v-spacer class="mx-2"></v-spacer>
              <v-text-field
                v-model="registerAccount.streetName"
                label="Rue"
                placeholder="Rue de l'exemple"
                hide-details="auto"
                outlined
                required
              >
              </v-text-field>
            </v-row>
          </div>

          <div class="form-group">
            <v-row v-bind:no-gutters="true">
              <v-text-field v-model="registerAccount.city" label="Ville" placeholder="Grenoble" hide-details="auto" outlined required>
              </v-text-field>
              <v-spacer class="mx-2"></v-spacer>
              <v-text-field v-model="registerAccount.zipCode" label="Code postal" placeholder="38000" hide-details="auto" outlined required>
              </v-text-field>
            </v-row>
          </div>

          <button type="submit" :disabled="$v.$invalid" class="btn btn-primary" v-text="$t('register.form.button')" data-cy="submit">
            Register
          </button>
        </form>
        <!-- <p></p>
        <div class="alert alert-warning">
          <span v-text="$t('global.messages.info.authenticated.prefix')">If you want to </span>
          <a class="alert-link" v-on:click="openLogin()" v-text="$t('global.messages.info.authenticated.link')">sign in</a
          ><span v-html="$t('global.messages.info.authenticated.suffix')"
            >, you can try the default accounts:<br />- Administrator (login="admin" and password="admin") <br />- User (login="user" and
            password="user").</span
          >
        </div> -->
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./register.component.ts"></script>
