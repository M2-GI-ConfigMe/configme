<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.order.home.createOrEditLabel"
          data-cy="OrderCreateUpdateHeading"
          v-text="$t('configmeApp.order.home.createOrEditLabel')"
        >
          Create or edit a Order
        </h2>
        <div>
          <div class="form-group" v-if="order.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="order.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.order.createdAt')" for="order-createdAt">Created At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="order-createdAt"
                  v-model="$v.order.createdAt.$model"
                  name="createdAt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="order-createdAt"
                data-cy="createdAt"
                type="text"
                class="form-control"
                name="createdAt"
                :class="{ valid: !$v.order.createdAt.$invalid, invalid: $v.order.createdAt.$invalid }"
                v-model="$v.order.createdAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.order.createdAt.$anyDirty && $v.order.createdAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.order.createdAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.order.updatedAt')" for="order-updatedAt">Updated At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="order-updatedAt"
                  v-model="$v.order.updatedAt.$model"
                  name="updatedAt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="order-updatedAt"
                data-cy="updatedAt"
                type="text"
                class="form-control"
                name="updatedAt"
                :class="{ valid: !$v.order.updatedAt.$invalid, invalid: $v.order.updatedAt.$invalid }"
                v-model="$v.order.updatedAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.order.updatedAt.$anyDirty && $v.order.updatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.order.updatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.order.validatedAt')" for="order-validatedAt">Validated At</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="order-validatedAt"
                  v-model="$v.order.validatedAt.$model"
                  name="validatedAt"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="order-validatedAt"
                data-cy="validatedAt"
                type="text"
                class="form-control"
                name="validatedAt"
                :class="{ valid: !$v.order.validatedAt.$invalid, invalid: $v.order.validatedAt.$invalid }"
                v-model="$v.order.validatedAt.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.order.validatedAt.$anyDirty && $v.order.validatedAt.$invalid">
              <small class="form-text text-danger" v-if="!$v.order.validatedAt.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.order.status')" for="order-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.order.status.$invalid, invalid: $v.order.status.$invalid }"
              v-model="$v.order.status.$model"
              id="order-status"
              data-cy="status"
              required
            >
              <option value="CART" v-bind:label="$t('configmeApp.OrderStatus.CART')">CART</option>
              <option value="PROCESSING" v-bind:label="$t('configmeApp.OrderStatus.PROCESSING')">PROCESSING</option>
              <option value="PAYED" v-bind:label="$t('configmeApp.OrderStatus.PAYED')">PAYED</option>
              <option value="FAILED" v-bind:label="$t('configmeApp.OrderStatus.FAILED')">FAILED</option>
            </select>
            <div v-if="$v.order.status.$anyDirty && $v.order.status.$invalid">
              <small class="form-text text-danger" v-if="!$v.order.status.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.order.deliveryAddress')" for="order-deliveryAddress"
              >Delivery Address</label
            >
            <select
              class="form-control"
              id="order-deliveryAddress"
              data-cy="deliveryAddress"
              name="deliveryAddress"
              v-model="order.deliveryAddress"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  order.deliveryAddress && addressOption.id === order.deliveryAddress.id ? order.deliveryAddress : addressOption
                "
                v-for="addressOption in addresses"
                :key="addressOption.id"
              >
                {{ addressOption.id }}
              </option>
            </select>
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
            :disabled="$v.order.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./order-update.component.ts"></script>
