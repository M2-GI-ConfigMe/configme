<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.orderLine.home.createOrEditLabel"
          data-cy="OrderLineCreateUpdateHeading"
          v-text="$t('configmeApp.orderLine.home.createOrEditLabel')"
        >
          Create or edit a OrderLine
        </h2>
        <div>
          <div class="form-group" v-if="orderLine.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="orderLine.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.orderLine.config')" for="order-line-config">Config</label>
            <select class="form-control" id="order-line-config" data-cy="config" name="config" v-model="orderLine.config" required>
              <option v-if="!orderLine.config" v-bind:value="null" selected></option>
              <option
                v-bind:value="orderLine.config && clientConfigOption.id === orderLine.config.id ? orderLine.config : clientConfigOption"
                v-for="clientConfigOption in clientConfigs"
                :key="clientConfigOption.id"
              >
                {{ clientConfigOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.orderLine.config.$anyDirty && $v.orderLine.config.$invalid">
            <small class="form-text text-danger" v-if="!$v.orderLine.config.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.orderLine.order')" for="order-line-order">Order</label>
            <select class="form-control" id="order-line-order" data-cy="order" name="order" v-model="orderLine.order" required>
              <option v-if="!orderLine.order" v-bind:value="null" selected></option>
              <option
                v-bind:value="orderLine.order && orderOption.id === orderLine.order.id ? orderLine.order : orderOption"
                v-for="orderOption in orders"
                :key="orderOption.id"
              >
                {{ orderOption.id }}
              </option>
            </select>
          </div>
          <div v-if="$v.orderLine.order.$anyDirty && $v.orderLine.order.$invalid">
            <small class="form-text text-danger" v-if="!$v.orderLine.order.required" v-text="$t('entity.validation.required')">
              This field is required.
            </small>
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
            :disabled="$v.orderLine.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./order-line-update.component.ts"></script>
