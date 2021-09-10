<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.product.home.createOrEditLabel"
          data-cy="ProductCreateUpdateHeading"
          v-text="$t('configmeApp.product.home.createOrEditLabel')"
        >
          Create or edit a Product
        </h2>
        <div>
          <div class="form-group" v-if="product.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="product.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.name')" for="product-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="product-name"
              data-cy="name"
              :class="{ valid: !$v.product.name.$invalid, invalid: $v.product.name.$invalid }"
              v-model="$v.product.name.$model"
              required
            />
            <div v-if="$v.product.name.$anyDirty && $v.product.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.product.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.price')" for="product-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="product-price"
              data-cy="price"
              :class="{ valid: !$v.product.price.$invalid, invalid: $v.product.price.$invalid }"
              v-model.number="$v.product.price.$model"
              required
            />
            <div v-if="$v.product.price.$anyDirty && $v.product.price.$invalid">
              <small class="form-text text-danger" v-if="!$v.product.price.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.product.price.min" v-text="$t('entity.validation.min', { min: 0.5 })">
                This field should be at least 0.5.
              </small>
              <small class="form-text text-danger" v-if="!$v.product.price.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.discount')" for="product-discount">Discount</label>
            <input
              type="number"
              class="form-control"
              name="discount"
              id="product-discount"
              data-cy="discount"
              :class="{ valid: !$v.product.discount.$invalid, invalid: $v.product.discount.$invalid }"
              v-model.number="$v.product.discount.$model"
            />
            <div v-if="$v.product.discount.$anyDirty && $v.product.discount.$invalid">
              <small class="form-text text-danger" v-if="!$v.product.discount.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.product.discount.max" v-text="$t('entity.validation.max', { max: 1 })">
                This field cannot be longer than 1 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.product.discount.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.stock')" for="product-stock">Stock</label>
            <input
              type="number"
              class="form-control"
              name="stock"
              id="product-stock"
              data-cy="stock"
              :class="{ valid: !$v.product.stock.$invalid, invalid: $v.product.stock.$invalid }"
              v-model.number="$v.product.stock.$model"
              required
            />
            <div v-if="$v.product.stock.$anyDirty && $v.product.stock.$invalid">
              <small class="form-text text-danger" v-if="!$v.product.stock.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.product.stock.min" v-text="$t('entity.validation.min', { min: 0 })">
                This field should be at least 0.
              </small>
              <small class="form-text text-danger" v-if="!$v.product.stock.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.img')" for="product-img">Img</label>
            <input
              type="text"
              class="form-control"
              name="img"
              id="product-img"
              data-cy="img"
              :class="{ valid: !$v.product.img.$invalid, invalid: $v.product.img.$invalid }"
              v-model="$v.product.img.$model"
              required
            />
            <div v-if="$v.product.img.$anyDirty && $v.product.img.$invalid">
              <small class="form-text text-danger" v-if="!$v.product.img.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.desc')" for="product-desc">Desc</label>
            <input
              type="text"
              class="form-control"
              name="desc"
              id="product-desc"
              data-cy="desc"
              :class="{ valid: !$v.product.desc.$invalid, invalid: $v.product.desc.$invalid }"
              v-model="$v.product.desc.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.brand')" for="product-brand">Brand</label>
            <input
              type="text"
              class="form-control"
              name="brand"
              id="product-brand"
              data-cy="brand"
              :class="{ valid: !$v.product.brand.$invalid, invalid: $v.product.brand.$invalid }"
              v-model="$v.product.brand.$model"
              required
            />
            <div v-if="$v.product.brand.$anyDirty && $v.product.brand.$invalid">
              <small class="form-text text-danger" v-if="!$v.product.brand.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.isActive')" for="product-isActive">Is Active</label>
            <input
              type="checkbox"
              class="form-check"
              name="isActive"
              id="product-isActive"
              data-cy="isActive"
              :class="{ valid: !$v.product.isActive.$invalid, invalid: $v.product.isActive.$invalid }"
              v-model="$v.product.isActive.$model"
              required
            />
            <div v-if="$v.product.isActive.$anyDirty && $v.product.isActive.$invalid">
              <small class="form-text text-danger" v-if="!$v.product.isActive.required" v-text="$t('entity.validation.required')">
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
            :disabled="$v.product.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./product-update.component.ts"></script>
