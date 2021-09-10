<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.dimension.home.createOrEditLabel"
          data-cy="DimensionCreateUpdateHeading"
          v-text="$t('configmeApp.dimension.home.createOrEditLabel')"
        >
          Create or edit a Dimension
        </h2>
        <div>
          <div class="form-group" v-if="dimension.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="dimension.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.dimension.height')" for="dimension-height">Height</label>
            <input
              type="number"
              class="form-control"
              name="height"
              id="dimension-height"
              data-cy="height"
              :class="{ valid: !$v.dimension.height.$invalid, invalid: $v.dimension.height.$invalid }"
              v-model.number="$v.dimension.height.$model"
              required
            />
            <div v-if="$v.dimension.height.$anyDirty && $v.dimension.height.$invalid">
              <small class="form-text text-danger" v-if="!$v.dimension.height.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.dimension.height.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.dimension.width')" for="dimension-width">Width</label>
            <input
              type="number"
              class="form-control"
              name="width"
              id="dimension-width"
              data-cy="width"
              :class="{ valid: !$v.dimension.width.$invalid, invalid: $v.dimension.width.$invalid }"
              v-model.number="$v.dimension.width.$model"
              required
            />
            <div v-if="$v.dimension.width.$anyDirty && $v.dimension.width.$invalid">
              <small class="form-text text-danger" v-if="!$v.dimension.width.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.dimension.width.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.dimension.length')" for="dimension-length">Length</label>
            <input
              type="number"
              class="form-control"
              name="length"
              id="dimension-length"
              data-cy="length"
              :class="{ valid: !$v.dimension.length.$invalid, invalid: $v.dimension.length.$invalid }"
              v-model.number="$v.dimension.length.$model"
              required
            />
            <div v-if="$v.dimension.length.$anyDirty && $v.dimension.length.$invalid">
              <small class="form-text text-danger" v-if="!$v.dimension.length.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.dimension.length.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
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
            :disabled="$v.dimension.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./dimension-update.component.ts"></script>
