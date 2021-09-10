<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.address.home.createOrEditLabel"
          data-cy="AddressCreateUpdateHeading"
          v-text="$t('configmeApp.address.home.createOrEditLabel')"
        >
          Create or edit a Address
        </h2>
        <div>
          <div class="form-group" v-if="address.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="address.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.address.zipCode')" for="address-zipCode">Zip Code</label>
            <input
              type="text"
              class="form-control"
              name="zipCode"
              id="address-zipCode"
              data-cy="zipCode"
              :class="{ valid: !$v.address.zipCode.$invalid, invalid: $v.address.zipCode.$invalid }"
              v-model="$v.address.zipCode.$model"
              required
            />
            <div v-if="$v.address.zipCode.$anyDirty && $v.address.zipCode.$invalid">
              <small class="form-text text-danger" v-if="!$v.address.zipCode.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.address.city')" for="address-city">City</label>
            <input
              type="text"
              class="form-control"
              name="city"
              id="address-city"
              data-cy="city"
              :class="{ valid: !$v.address.city.$invalid, invalid: $v.address.city.$invalid }"
              v-model="$v.address.city.$model"
              required
            />
            <div v-if="$v.address.city.$anyDirty && $v.address.city.$invalid">
              <small class="form-text text-danger" v-if="!$v.address.city.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.address.streetNumber')" for="address-streetNumber"
              >Street Number</label
            >
            <input
              type="text"
              class="form-control"
              name="streetNumber"
              id="address-streetNumber"
              data-cy="streetNumber"
              :class="{ valid: !$v.address.streetNumber.$invalid, invalid: $v.address.streetNumber.$invalid }"
              v-model="$v.address.streetNumber.$model"
              required
            />
            <div v-if="$v.address.streetNumber.$anyDirty && $v.address.streetNumber.$invalid">
              <small class="form-text text-danger" v-if="!$v.address.streetNumber.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.address.streetName')" for="address-streetName">Street Name</label>
            <input
              type="text"
              class="form-control"
              name="streetName"
              id="address-streetName"
              data-cy="streetName"
              :class="{ valid: !$v.address.streetName.$invalid, invalid: $v.address.streetName.$invalid }"
              v-model="$v.address.streetName.$model"
              required
            />
            <div v-if="$v.address.streetName.$anyDirty && $v.address.streetName.$invalid">
              <small class="form-text text-danger" v-if="!$v.address.streetName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.address.complementary')" for="address-complementary"
              >Complementary</label
            >
            <input
              type="text"
              class="form-control"
              name="complementary"
              id="address-complementary"
              data-cy="complementary"
              :class="{ valid: !$v.address.complementary.$invalid, invalid: $v.address.complementary.$invalid }"
              v-model="$v.address.complementary.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.address.firstName')" for="address-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="address-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.address.firstName.$invalid, invalid: $v.address.firstName.$invalid }"
              v-model="$v.address.firstName.$model"
              required
            />
            <div v-if="$v.address.firstName.$anyDirty && $v.address.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.address.firstName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.address.lastName')" for="address-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="address-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.address.lastName.$invalid, invalid: $v.address.lastName.$invalid }"
              v-model="$v.address.lastName.$model"
              required
            />
            <div v-if="$v.address.lastName.$anyDirty && $v.address.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.address.lastName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.address.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./address-update.component.ts"></script>
