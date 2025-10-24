# 失物招领系统 API 文档

## 基础信息
- **Base URL**: `http://localhost:8081`
- **文档格式**: OpenAPI 3.0
- **Swagger UI**: `http://localhost:8081/swagger-ui.html`

## 通用响应格式
```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

## 失物相关接口

### 1. 发布失物
**接口描述**: 管理员发布新的失物信息

**接口地址**: `POST /api/lost-items/publish`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| adminId | Long | 是 | 管理员ID |
| name | String | 是 | 失物名称 |
| type | String | 是 | 失物类型 |
| color | String | 否 | 颜色 |
| brand | String | 否 | 品牌 |
| location | String | 是 | 发现地点 |
| description | String | 否 | 详细描述 |
| image | File | 否 | 失物图片 |

**请求示例**:
```bash
curl -X POST "http://localhost:8081/api/lost-items/publish" \
  -H "Content-Type: multipart/form-data" \
  -F "adminId=1" \
  -F "name=小米手机" \
  -F "type=电子产品" \
  -F "color=黑色" \
  -F "brand=小米" \
  -F "location=图书馆一楼" \
  -F "description=黑色小米手机一部，屏幕完好" \
  -F "image=@phone.jpg"
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "小米手机",
    "type": "电子产品",
    "color": "黑色",
    "brand": "小米",
    "location": "图书馆一楼",
    "description": "黑色小米手机一部，屏幕完好",
    "imageUrl": "/uploads/lost-items/phone.jpg",
    "isClaimed": false,
    "createdAt": "2024-01-15 10:30:00"
  }
}
```

### 2. 搜索失物
**接口描述**: 根据条件搜索失物信息

**接口地址**: `GET /api/lost-items/search`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| keyword | String | 否 | 关键词搜索（名称、描述、位置） |
| type | String | 否 | 失物类型 |
| color | String | 否 | 颜色 |
| brand | String | 否 | 品牌 |
| location | String | 否 | 发现地点 |
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 每页数量，默认10 |

**请求示例**:
```bash
curl "http://localhost:8081/api/lost-items/search?keyword=手机&type=电子产品&pageNum=1&pageSize=10"
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "name": "小米手机",
        "type": "电子产品",
        "color": "黑色",
        "brand": "小米",
        "location": "图书馆一楼",
        "description": "黑色小米手机一部",
        "imageUrl": "/uploads/lost-items/phone.jpg",
        "isClaimed": false,
        "createdAt": "2024-01-15 10:30:00"
      }
    ],
    "total": 1,
    "size": 10,
    "current": 1
  }
}
```

### 3. 领取失物
**接口描述**: 用户领取失物

**接口地址**: `POST /api/lost-items/claim`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| lostItemId | Long | 是 | 失物ID |
| claimantName | String | 是 | 领取人姓名 |
| claimantContact | String | 是 | 领取人联系方式 |
| claimantIdCard | String | 否 | 领取人身份证号 |
| description | String | 否 | 领取描述 |

**请求示例**:
```bash
curl -X POST "http://localhost:8081/api/lost-items/claim" \
  -H "Content-Type: application/json" \
  -d '{
    "lostItemId": 1,
    "claimantName": "张三",
    "claimantContact": "13800138000",
    "claimantIdCard": "110101199001011234",
    "description": "这是我的手机"
  }'
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

### 4. 获取失物详情
**接口描述**: 根据ID获取失物详细信息

**接口地址**: `GET /api/lost-items/{id}`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| id | Long | 是 | 失物ID |

**请求示例**:
```bash
curl "http://localhost:8081/api/lost-items/1"
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "小米手机",
    "type": "电子产品",
    "color": "黑色",
    "brand": "小米",
    "location": "图书馆一楼",
    "description": "黑色小米手机一部，屏幕完好",
    "imageUrl": "/uploads/lost-items/phone.jpg",
    "isClaimed": false,
    "createdAt": "2024-01-15 10:30:00",
    "updatedAt": "2024-01-15 10:30:00"
  }
}
```

### 5. 获取所有未领取失物
**接口描述**: 获取所有未领取的失物列表

**接口地址**: `GET /api/lost-items/all`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 每页数量，默认10 |

**请求示例**:
```bash
curl "http://localhost:8081/api/lost-items/all?pageNum=1&pageSize=10"
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "name": "小米手机",
        "type": "电子产品",
        "color": "黑色",
        "brand": "小米",
        "location": "图书馆一楼",
        "isClaimed": false,
        "createdAt": "2024-01-15 10:30:00"
      }
    ],
    "total": 1,
    "size": 10,
    "current": 1
  }
}
```

### 6. 获取管理员发布的失物
**接口描述**: 获取指定管理员发布的失物列表

**接口地址**: `GET /api/lost-items/admin/{adminId}`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| adminId | Long | 是 | 管理员ID |
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 每页数量，默认10 |

**请求示例**:
```bash
curl "http://localhost:8081/api/lost-items/admin/1?pageNum=1&pageSize=10"
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [
      {
        "id": 1,
        "name": "小米手机",
        "type": "电子产品",
        "color": "黑色",
        "brand": "小米",
        "location": "图书馆一楼",
        "isClaimed": false,
        "createdAt": "2024-01-15 10:30:00"
      }
    ],
    "total": 1,
    "size": 10,
    "current": 1
  }
}
```

### 7. 删除失物
**接口描述**: 删除失物信息

**接口地址**: `DELETE /api/lost-items/{id}`

**请求参数**:
| 参数名 | 类型 | 必填 | 描述 |
|--------|------|------|------|
| id | Long | 是 | 失物ID |
| adminId | Long | 是 | 管理员ID |
| isSuperAdmin | Boolean | 否 | 是否为超级管理员，默认false |

**请求示例**:
```bash
curl -X DELETE "http://localhost:8081/api/lost-items/1?adminId=1"
```

**响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": true
}
```

## 错误码说明
| 错误码 | 描述 |
|--------|------|
| 200 | 成功 |
| 400 | 参数错误 |
| 401 | 未授权 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 测试数据准备

### 1. 创建测试管理员
```sql
INSERT INTO users (id, username, password, email, phone, role, created_at, updated_at) 
VALUES (1, 'admin1', 'password123', 'admin1@example.com', '13800138001', 'ADMIN', NOW(), NOW());
```

### 2. 创建测试失物数据
```bash
# 发布失物测试
curl -X POST "http://localhost:8081/api/lost-items/publish" \
  -H "Content-Type: multipart/form-data" \
  -F "adminId=1" \
  -F "name=测试手机" \
  -F "type=电子产品" \
  -F "color=白色" \
  -F "brand=华为" \
  -F "location=教学楼A栋" \
  -F "description=白色华为手机一部，完好无损"

# 搜索失物测试
curl "http://localhost:8081/api/lost-items/search?keyword=手机&pageNum=1&pageSize=5"

# 领取失物测试
curl -X POST "http://localhost:8081/api/lost-items/claim" \
  -H "Content-Type: application/json" \
  -d '{
    "lostItemId": 1,
    "claimantName": "测试用户",
    "claimantContact": "13800138000",
    "description": "这是我的手机"
  }'
```